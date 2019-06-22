<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('member:add') && listQuery.type==='1'" @click="loadPerms">加载权限</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tree
      :data="list"
      show-checkbox
      node-key="id"
      :default-checked-keys="checkList"
      :default-expand-all="true"
      :props="defaultProps">
    </el-tree>
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
        checkList:[],
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
        },
        defaultProps: {
          children: 'children',
          label: 'name'
        },
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
          this.list = response.data.children;
          this.checkList = response.data.checkList;
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
