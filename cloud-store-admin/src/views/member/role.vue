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
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column align="center" label="序号" width="80px">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色名称" prop="name"/>
      <el-table-column align="center" label="角色描述" prop="description"/>
      <el-table-column align="center" label="角色状态" prop="state">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.state===1">正常</el-tag>
          <el-tag type="warning" v-else-if="scope.row.state===2">禁用</el-tag>
          <el-tag type="warning" v-else-if="scope.row.state===3">已删除</el-tag>
          <el-tag type="danger" v-else>角色状态异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色类型" prop="type">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.type===1">可授权</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===2">不可授权</el-tag>
          <el-tag type="danger" v-else>角色类型异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" :formatter="dateFormat" prop="createTime"/>
      <el-table-column align="center" label="最近修改时间" :formatter="dateFormat" prop="updateTime"/>
      <el-table-column align="center" label="修改人" prop="updateUser"/>
      <el-table-column align="center" label="管理" width="320px" v-if="hasPerm('role:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showSetPerms(scope.$index)" size="medium">设置权限</el-button>
          <el-button type="primary" icon="" size="medium"
                     @click="removeUser(scope.$index)">设置角色</el-button>
          <el-button type="danger" icon="el-icon-delete" size="medium"
                     @click="deleteRole(scope.$index)"></el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="450px">
      <el-form class="small-space" :model="tempRole" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="角色名" required v-if="dialogStatus==='create'">
          <el-input type="text" v-model="tempRole.name">
          </el-input>
        </el-form-item>
        <el-form-item label="角色编码" required v-if="dialogStatus==='create'">
          <el-input type="text" v-model="tempRole.code">
          </el-input>
        </el-form-item>
        <el-form-item label="角色描述" v-if="dialogStatus==='create'">
          <el-input type="text" v-model="tempRole.description">
          </el-input>
        </el-form-item>
        <el-form-item label="设置权限" required>
          <div v-if="dialogStatus==='create'">
            <el-radio v-model="tempRole.isSetPerms" label="1">是</el-radio>
            <el-radio v-model="tempRole.isSetPerms" label="2">否</el-radio>
          </div>
          <el-tree
            :data="perms"
            show-checkbox
            @check-change="checkChange"
            :default-checked-keys="tempRole.permChecks"
            node-key="id"
            ref="permsTree"
            :props="defaultProps"
            v-if="tempRole.isSetPerms==='1'">
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
  import * as roleService from '@/api/member/role'
  import * as permsService from '@/api/member/permissions'
  import {mapGetters} from 'vuex'


  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
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
          create: '新建角色'
        },
        tempRole: {
          id: '',
          name: '',
          code: '',
          isSetPerms: '2',
          description: '',
          permChecks: []
        },
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        multipleSelection:[],

      }
    },
    created() {
      this.getList();
      if (this.hasPerm('role:add') || this.hasPerm('role:update')) {
        this.getPerms();
      }
    },
    computed: {
      ...mapGetters([
        'memberId'
      ])
    },
    methods: {
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      dateFormat: function (row, column) {
        let moment = require('moment');
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
      showCreate() {
        //显示新增对话框
        this.tempRole.name = "";
        this.tempRole.permChecks = [];
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      batchDelete(){
        let selection = this.multipleSelection;
        //批量删除
        let arr = selection.map(function (item) {
          return item.id;
        });
        if(arr.length <= 0){
          this.$message.error("请选择需要删除的角色！")
        }
        this.$confirm('确定批量删除角色?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          roleService.batchDelete({'ids':arr}).then(() => {
            this.getList()
          }).catch(() => {
            this.$message.error("删除失败")
          })
        })
      },
      showSetPerms($index) {
        let role = this.list[$index];
        console.log(role,'role');
        this.tempRole.name = role.name;
        this.tempRole.code = role.code;
        this.tempRole.description = role.description;
        this.tempRole.isSetPerms='1';
        this.tempRole.id = role.id;
        this.dialogStatus = "setPerms";
        permsService.getCheckPerms(this.tempRole.id).then(response => {
          this.$refs.permsTree.setCheckedKeys(response.data);
        });

        this.dialogFormVisible = true
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
        let _vue = this;
        roleService.updateRole(this.tempRole).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false;
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              _vue.getList();
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
        permsService.getPerms().then(response => {
          this.perms = response.data.children;
        })
      },
      checkChange(){
        this.tempRole.permChecks =  this.$refs.permsTree.getCheckedKeys();
      }
    }
  }
</script>
<style>
  .select-disabled {
    pointer-events: none;
    opacity: 0.6;
  }
</style>
