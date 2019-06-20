package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:00
 *
 * @Author: JackZhan
 */
public interface IPermissionsService {


    List<PermissionsDTO> list();

    boolean saveList(PermissionsRequest request);
}
