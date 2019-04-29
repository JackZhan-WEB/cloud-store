package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.module.entities.Dept;
import club.jackzhan.cloudstore.service.DeptService;
import club.jackzhan.cloudstore.mapper.DeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: JackZhan
 * Date: 2019-03-21
 * Time: 16:35
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public boolean add(Dept dept) {
        return deptDao.addDept(dept);
    }

    @Override
    public Dept get(Long id) {
        return deptDao.findById(id);
    }

    @Override
    public List<Dept> list() {
        return deptDao.findAll();
    }

}

