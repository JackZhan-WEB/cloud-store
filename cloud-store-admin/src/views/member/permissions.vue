<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('member:add') && listQuery.type==='1'" @click="loadPerms">加载权限</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit highlight-current-row>
      <el-table-column align="center" label="序号" width="80px">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="权限名称" prop="name"/>
      <el-table-column align="center" label="权限描述" prop="description"/>
      <el-table-column align="center" label="权限code" prop="code"/>
      <el-table-column align="center" label="权限类型" prop="type">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.state===1">可授权</el-tag>
          <el-tag type="primary" v-else-if="scope.row.state===2">不可授权</el-tag>
          <el-tag type="danger" v-else>权限异常</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" :formatter="dateFormat" prop="createTime"/>
      <el-table-column align="center" label="管理" width="220px" v-if="hasPerm('member:update')">
        <template slot-scope="scope">
          <el-button type="danger" icon="delete" v-if="scope.row.memberId!==memberId"
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
  </div>
</template>
<script>
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
          id: '',
          username: '',
          phone: '',
          password: '',
          nickname: '',
          roles: [],
          memberId: ''
        }
      }
    },
    created() {
      this.getList();
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
      getList() {
        //查询列表
        this.listLoading = true;
        console.log(this.listQuery, 'listQuery');
        permsService.getList(this.listQuery).then(response => {
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
      loadPerms(){
        //加载权限
        this.listLoading = true;
        permsService.loadPerms(this.listQuery).then(response => {
          this.listLoading = false;
          this.getList();
        })
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
