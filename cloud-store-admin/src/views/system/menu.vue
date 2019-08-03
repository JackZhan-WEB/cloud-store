<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('role:add') && listQuery.type==='1'" @click="showCreate">
            添加
          </el-button>
          <el-button type="primary" icon="plus" v-if="hasPerm('role:batchDelete')"
                     @click="batchDelete">批量删除
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="dataList" row-key="menuId" border style="width: 100%; ">
      <el-table-column prop="name" header-align="center" min-width="150" label="名称"/>
      <el-table-column prop="parentName" header-align="center" align="center" width="120" label="上级菜单"/>
      <el-table-column
        header-align="center" align="center" label="图标">
        <template slot-scope="scope">
          <icon-svg :name="scope.row.icon || ''"></icon-svg>
        </template>
      </el-table-column>
      <el-table-column prop="type" header-align="center" align="center" label="类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 0" size="small">目录</el-tag>
          <el-tag v-else-if="scope.row.type === 1" size="small" type="success">菜单</el-tag>
          <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderNum" header-align="center" align="center" label="排序号">
      </el-table-column>
      <el-table-column prop="url" header-align="center" align="center" width="150" :show-overflow-tooltip="true" label="菜单URL"/>
      <el-table-column prop="perms" header-align="center" align="center" width="150" :show-overflow-tooltip="true" label="授权标识"/>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button v-if="" type="text" size="small" @click="">修改</el-button>
          <el-button v-if="" type="text" size="small" @click="">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.currentPage"
      :page-size="listQuery.pageSize"
      :total="totalCount"
      :page-sizes="[1, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="450px" @close="clearDialog">
      <el-form class="small-space" :model="tempRole" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="角色名" required>
          <el-input type="text" v-model="tempRole.name">
          </el-input>
        </el-form-item>
        <el-form-item label="角色编码" required>
          <el-input type="text" v-model="tempRole.code">
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input type="text" v-model="tempRole.description">
          </el-input>
        </el-form-item>
        <el-form-item label="设置权限" required>
          <div>
            <el-radio v-model="tempRole.isSetPerms" label="1">是</el-radio>
            <el-radio v-model="tempRole.isSetPerms" label="2">否</el-radio>
          </div>
          <el-tree
            :data="perms"
            show-checkbox
            @check-change="checkPerms"
            :default-checked-keys="tempRole.permChecks"
            node-key="id"
            ref="permsTree"
            :props="defaultProps">
          </el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus==='create'" type="success" @click="createRole">创 建</el-button>
        <el-button type="primary" v-else @click="updateRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import * as roleService from '@/api/system/role'
  import * as permsService from '@/api/system/permissions'


  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        roleList: [],//dialog表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          type: '1',
          currentPage: 1,//页码
          pageSize: 20,//每页条数
        },
        perms: [],//所有的权限
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          setPerms: '设置权限',
          setRoles: '设置角色',
          create: '新建角色'
        },
        tempRole: {
          id: '',
          name: '',
          code: '',
          isSetPerms: '2',
          description: '',
          permChecks: [],
          roleChecks: [],
        },
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        multipleSelection: [],

      }
    },
    created() {
      this.getList();
      if (this.hasPerm('role:add') || this.hasPerm('role:update')) {
        this.getPerms();
      }
    },
    methods: {
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      dateFormat: function (row, column) {
        let moment = require('moment/moment');
        let date = row[column.property];
        if (!date) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      getList() {
        //查询列表
        this.listLoading = true;
        roleService.getList(this.listQuery).then(response => {
          this.listLoading = false;
          this.list = response.data.records;
          this.totalCount = response.data.totalCount;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageSize = val;
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.currentPage = val;
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.currentPage = 1;
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.currentPage - 1) * this.listQuery.pageSize + $index + 1
      },
      clearDialog() {
        //关闭dialog清空数据
        this.tempRole.id = '';
        this.tempRole.name = '';
        this.tempRole.code = '';
        this.tempRole.isSetPerms = '1';
        this.tempRole.description = '';
        this.tempRole.permChecks = [];
        this.tempRole.roleChecks = [];
        this.$refs.permsTree.setCheckedKeys([]);
        // this.tempRole.roleChecks = [];
        // this.$refs.checkRolesTable.clearSelection();
      },
      showCreate() {
        console.log(this.tempRole,'open');
        //显示新增对话框
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      batchDelete() {
        let selection = this.multipleSelection;
        //批量删除
        let arr = selection.map(function (item) {
          return item.id;
        });
        if (arr.length <= 0) {
          this.$message.error("请选择需要删除的角色！")
        }
        this.$confirm('确定批量删除角色?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          roleService.batchDelete({'ids': arr}).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("删除失败")
          })
        })
      },
      showSetPerms($index) {
        let role = this.list[$index];
        console.log(role, 'role');
        this.tempRole.name = role.name;
        this.tempRole.code = role.code;
        this.tempRole.description = role.description;
        this.tempRole.isSetPerms = '1';
        this.tempRole.id = role.id;
        this.dialogStatus = "setPerms";
        permsService.getCheckPerms(this.tempRole.id).then(response => {
          this.$refs.permsTree.setCheckedKeys(response.data);
        });
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let role = this.list[$index];
        console.log(role, 'role');
        this.tempRole.name = role.name;
        this.tempRole.code = role.code;
        this.tempRole.description = role.description;
        this.tempRole.isSetPerms = '1';
        this.tempRole.id = role.id;
        this.dialogStatus = "setPerms";
        permsService.getCheckPerms(this.tempRole.id).then(response => {
          this.$refs.permsTree.setCheckedKeys(response.data);
        });
        this.dialogFormVisible = true
      },
      showSetRoles($index) {
        let role = this.list[$index];
        this.tempRole.name = role.name;
        this.tempRole.code = role.code;
        this.tempRole.description = role.description;
        this.tempRole.isSetPerms = '1';
        this.tempRole.id = role.id;
        this.dialogStatus = "setRoles";
        //回显角色
        roleService.getCheckRoles({'id': this.tempRole.id}).then(response => {
          let rows = response.data;
          let newRoles = [];
          this.list.forEach(item => {
            if (item.id !== this.tempRole.id) {
              newRoles.push(item);
            }
            rows.forEach(row => {
              if (item.id === row) {
                this.$refs.checkRolesTable.toggleRowSelection(item, true);
              }
            });
          });
          this.roleList = newRoles;
          this.tempRole.roleChecks = response.data;
        });
        this.dialogFormVisible = true
      },
      filterHandler(value, row, column) {
        console.log(row, 'row');
      },
      createRole() {
        //添加新角色
        roleService.createRole(this.tempRole).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateRole() {
        //修改角色
        // let selection = this.$refs.checkRolesTable.selection;
        // this.tempRole.roleChecks = selection.map(function (item) {
        //   console.log(item, 'item');
        //   return item.id;
        // });

        roleService.updateRole(this.tempRole).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false;
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              this.getList();
            }
          })
        })
      },
      deleteRole($index) {
        let _vue = this;
        this.$confirm('确定删除此角色?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let role = _vue.list[$index];
          roleService.deleteRole(role.id).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      getPerms() {
        //回显权限
        permsService.getPerms().then(response => {
          this.perms = response.data.children;
        })
      },
      checkPerms() {
        //选择权限
        this.tempRole.permChecks = this.$refs.permsTree.getCheckedKeys();
      },
    }
  }
</script>
<style>
  .select-disabled {
    pointer-events: none;
    opacity: 0.6;
  }
</style>
