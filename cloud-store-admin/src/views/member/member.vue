<template>
  <div class="app-container">
    <div>
      <el-tabs v-model="listQuery.type" type="card" @tab-click="selectByType">
        <el-tab-pane label="后台管理人员" name="1"></el-tab-pane>
        <el-tab-pane label="普通用户" name="2"></el-tab-pane>
        <el-tab-pane label="VIP用户" name="3"></el-tab-pane>
      </el-tabs>
    </div>
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('member:add') && listQuery.type==='1'"
                     @click="showCreate">添加
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
            <el-tag type="success" v-text="role.name" v-if="role.code==='admin'"/>
            <el-tag type="primary" v-text="role.name" v-else/>
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
          <el-tag type="success" v-if="scope.row.type===1">后台管理人员</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===2">普通用户</el-tag>
          <el-tag type="primary" v-else-if="scope.row.type===3">VIP用户</el-tag>
          <el-tag type="danger" v-else>账号异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" :formatter="dateFormat" prop="createTime"/>
      <el-table-column align="center" label="最近修改时间" :formatter="dateFormat" prop="updateTime"/>
      <el-table-column align="center" label="修改人" prop="updateUser"/>
      <el-table-column align="center" label="管理" width="320px" v-if="hasPerm('member:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showSetRole(scope.$index)" v-if="scope.row.type===1">设置角色
          </el-button>
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">编辑
          </el-button>
          <el-button type="danger" icon="el-icon-delete" v-if="scope.row.memberId!==memberId"
                     @click="removeUser(scope.$index)">
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
    <el-dialog :title="textMap[dialogStatus]" :close-on-click-modal="false" :visible.sync="dialogFormVisible"
               width="450px" :before-close="clearForm"
               :show-close=false>
      <el-form class="small-space" :model="tempUser" ref="tempUserForm" :rules="rules" label-position="left"
               label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="用户名" prop="username" v-if="dialogStatus!=='showSetRole'">
          <el-input type="text" v-model="tempUser.username">
          </el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname" v-if="dialogStatus!=='showSetRole'">
          <el-input type="text" v-model="tempUser.nickname">
          </el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone" v-if="dialogStatus!=='showSetRole'">
          <el-input type="text" v-model="tempUser.phone">
          </el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="dialogStatus==='create'">
          <el-input type="password" v-model="tempUser.password"/>
        </el-form-item>
        <!--        <el-form-item label="新密码" v-else>-->
        <!--          <el-input type="password" v-model="tempUser.password" placeholder="不填则表示不修改">-->
        <!--          </el-input>-->
        <!--        </el-form-item>-->
        <el-form-item label="角色" prop="roles">
          <el-select multiple v-model="tempUser.roles" value-key="id"
                     placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.id"
              :label="item.name"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clearForm">取 消</el-button>
        <el-button v-if="dialogStatus==='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary"  v-if="dialogStatus==='update'" @click="updateUser">确 定</el-button>
        <el-button type="primary" v-if="dialogStatus==='showSetRole'" @click="updateUserRole">设 置</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import * as memberService from '@/api/member/member'
  import * as roleService from '@/api/member/role'
  import {mapGetters} from 'vuex'


  export default {
    data() {
      //验证用户名是否存在
      let verifyUsername = (rule, value, callback) => {
        memberService.verifyUsername({'username': value, 'id': this.tempUser.id}).then(response => {
          if (!response.state) {
            callback(new Error(response.msg));
          } else {
            callback();
          }
        });
      };
      //验证手机是否存在
      let verifyPhone = (rule, value, callback) => {
        memberService.verifyPhone({'phone': value, 'id': this.tempUser.id}).then(response => {
          if (!response.state) {
            callback(new Error());
          } else {
            callback();
          }
        });
      };

      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          type: '1',
          currentPage: 1,//页码
          pageSize: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建用户',
          showSetRole: '设置角色'
        },
        tempUser: {
          id: '',
          username: '',
          phone: '',
          password: '',
          nickname: '',
          roles: [],
          memberId: ''
        },
        rules: {
          username: [
            {required: true, message: '请填写用户名', trigger: 'blur'},
            {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
            {validator: verifyUsername, message: '该用户名已经被注册', trigger: 'blur'}
          ],
          roles: [
            {required: true, message: '请选择角色', trigger: 'change'}
          ],
          phone: [
            //可以写正则表达式
            {required: true, pattern: /^[1]([3-9])[0-9]{9}$/, message: '目前只支持中国大陆的手机号码', trigger: 'blur'},
            {validator: verifyPhone, message: '该手机已经被注册', trigger: 'blur'}

          ],
          password: [
            {required: true, message: '请填写密码', trigger: 'blur'},
            {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
          ],
          nickname: [
            {required: true, message: '请填写昵称', trigger: 'blur'}
          ]
        }
      }
    },
    created() {
      this.getList();
      if (this.hasPerm('member:add') || this.hasPerm('member:update')) {
        this.getAllRoles();
      }
    },
    computed: {
      ...mapGetters([
        'memberId'
      ])
    },
    methods: {
      //关闭清空表单数据
      clearForm: function () {
        this.$refs.tempUserForm.resetFields();
        this.dialogFormVisible = false;
      },
      dateFormat: function (row, column) {
        let moment = require('moment');
        let date = row[column.property];
        if (!date) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      getAllRoles() {
        roleService.getAllRoles().then(response => {
          this.roles = response.data;
        })
      },
      getList() {
        //查询列表
        this.listLoading = true;
        console.log(this.listQuery, 'listQuery');
        memberService.getList(this.listQuery).then(response => {
          this.listLoading = false;
          this.list = response.data.records;
          this.totalCount = response.data.total;
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
        this.dialogStatus = "create";
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示编辑对话框
        let user = this.list[$index];
        console.log(user, 'showUpdate');
        this.tempUser.id = user.id;
        this.tempUser.username = user.username;
        this.tempUser.nickname = user.nickname;
        this.tempUser.phone = user.phone;
        this.tempUser.roles = user.roles;
        this.tempUser.memberId = user.memberId;
        this.tempUser.state = user.state;
        this.tempUser.password = '';
        this.dialogStatus = "update";
        this.dialogFormVisible = true
      },
      showSetRole($index) {
        //显示设置角色对话框
        let user = this.list[$index];
        console.log(user, 'showSetRole');
        this.tempUser.id = user.id;
        this.tempUser.username = user.username;
        this.tempUser.nickname = user.nickname;
        this.tempUser.roles = user.roles;
        this.tempUser.memberId = user.memberId;
        this.tempUser.state = user.state;
        this.tempUser.password = '';
        this.dialogStatus = "showSetRole";
        this.dialogFormVisible = true
      },
      createUser() {
        //添加新用户
        this.$refs.tempUserForm.validate((resp, field) => {
          if (resp) {
            memberService.createUser(this.tempUser).then(() => {
              this.getList();
              this.dialogFormVisible = false
            })
          }
        });
      },
      updateUser() {
        //修改用户
        console.log(this.tempUser, 'tempUser');
        this.$refs.tempUserForm.validate((resp, field) => {
          if (resp) {
            memberService.updateUser(this.tempUser).then(() => {
              this.dialogFormVisible = false;
              // if (this.memberId === this.tempUser.memberId) {
              //   msg = '修改成功,部分信息重新登录后生效'
              // }
              this.getList();
            })
          }
        });
      },
      updateUserRole() {
        //修改用户角色
        console.log(this.tempUser, 'tempUser');
        memberService.updateUser(this.tempUser).then(() => {
          this.dialogFormVisible = false;
          // if (this.memberId === this.tempUser.memberId) {
          //   msg = '修改成功,部分信息重新登录后生效'
          // }
          this.getList();
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
          console.log(user, 'user');
          user.state = '4';
          user.roles = null;
          memberService.updateUser(user).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
      selectByType() {
        this.getList();
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
