<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('user:add')" @click="showCreate">添加
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80px">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="昵称" prop="nickname"/>
      <el-table-column align="center" label="用户名" prop="username"/>
      <el-table-column align="center" label="手机号" prop="phone"/>
      <el-table-column align="center" label="角色">
        <template slot-scope="scope">
          <span v-for="role in scope.row.roles">
            &nbsp;
            <el-tag type="success" v-text="role.description" v-if="role.name==='admin'"/>
            <el-tag type="primary" v-text="role.description" v-else/>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户状态" prop="state">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.state===1">正常</el-tag>
          <el-tag type="warning" v-else-if="scope.row.state===2">密码错误次数过多被禁用</el-tag>
          <el-tag type="warning" v-else-if="scope.row.state===3">管理员禁用</el-tag>
          <el-tag type="info" v-else-if="scope.row.state===4">注销</el-tag>
          <el-tag type="danger" v-else>账号异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户类型" prop="type">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.type===1">超级管理员</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===2">后台管理人员</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===3">普通用户</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===4">VIP用户</el-tag>
          <el-tag type="danger" v-else>账号异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" :formatter="dateFormat" prop="createTime"/>
      <el-table-column align="center" label="最近修改时间" :formatter="dateFormat" prop="updateTime"/>
      <el-table-column align="center" label="管理" width="220px" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.memberId!=memberId"
                     @click="removeUser(scope.$index)">删除
          </el-button>
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempUser" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="用户名" required v-if="dialogStatus=='create'">
          <el-input type="text" v-model="tempUser.username">
          </el-input>
        </el-form-item>
        <el-form-item label="密码" v-if="dialogStatus=='create'" required>
          <el-input type="password" v-model="tempUser.password"/>
        </el-form-item>
        <el-form-item label="新密码" v-else>
          <el-input type="password" v-model="tempUser.password" placeholder="不填则表示不修改">
          </el-input>
        </el-form-item>
        <el-form-item label="角色" required>
          <el-select multiple v-model="tempUser.roles" value-key="id" placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.description"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称" required>
          <el-input type="text" v-model="tempUser.nickname">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary" v-else @click="updateUser">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import * as memberService from '@/api/member/member'
  import {mapGetters} from 'vuex'


  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          currentPage: 1,//页码
          pageSize: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建用户'
        },
        tempUser: {
          username: '',
          password: '',
          nickname: '',
          roles: [],
          memberId: ''
        }
      }
    },
    created() {
      this.getList();
      if (this.hasPerm('user:add') || this.hasPerm('user:update')) {
        this.getAllRoles();
      }
    },
    computed: {
      ...mapGetters([
        'memberId'
      ])
    },
    methods: {
      dateFormat: function (row, column) {
        let moment = require('moment');
        let date = row[column.property];
        if (!date) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      getAllRoles() {
        memberService.getAllRoles().then(response => {
          this.roles = response.data;
        })
      },
      getList() {
        //查询列表
        this.listLoading = true;
        memberService.getList(this.listQuery).then(response => {
          this.listLoading = false;
          this.list = response.data.pageData;
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
        this.tempUser.username = "";
        this.tempUser.password = "";
        this.tempUser.nickname = "";
        this.tempUser.roles = [];
        this.tempUser.memberId = "";
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let user = this.list[$index];
        this.tempUser.username = user.username;
        this.tempUser.nickname = user.nickname;
        this.tempUser.roles = user.roles;
        this.tempUser.memberId = user.memberId;
        this.tempUser.deleteStatus = '1';
        this.tempUser.password = '';
        this.dialogStatus = "update";
        this.dialogFormVisible = true
      },
      createUser() {
        //添加新用户
        memberService.createUser(this.tempUser).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/member/updateUser",
          method: "post",
          data: this.tempUser
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false;
          if (this.memberId === this.tempUser.memberId) {
            msg = '修改成功,部分信息重新登录后生效'
          }
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
      removeUser($index) {
        let _vue = this;
        this.$confirm('确定删除此用户?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          user.deleteStatus = '2';
          _vue.api({
            url: "/member/updateUser",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
    }
  }
</script>
