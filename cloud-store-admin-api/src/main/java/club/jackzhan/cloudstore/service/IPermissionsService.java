package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:39
 *
 * @Author: JackZhan
 */
public interface IPermissionsService {
    ResultResponse list();

    ResultResponse loadPerms();

    ResultResponse getPerms();

    ResultResponse getCheckPerms(BaseIdRequest<Integer> request);
}
