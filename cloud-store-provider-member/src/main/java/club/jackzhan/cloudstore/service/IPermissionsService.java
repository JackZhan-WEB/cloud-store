package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.PermissionsTreeDTO;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:00
 *
 * @Author: JackZhan
 */
public interface IPermissionsService {

    PermissionsTreeDTO list();

    boolean saveList(PermissionsRequest request);

    List<String> getAllCodes();

    PermissionsTreeDTO getPerms();

    List<Integer> getCheckPerms(BaseIdRequest<Integer> request);
}
