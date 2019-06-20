package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.entities.Dept;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: JackZhan
 * Date: 2019-03-21
 * Time: 16:34
 */
public interface DeptService {
   boolean add(Dept dept);

   Dept get(Long id);

   List<Dept> list();
}
