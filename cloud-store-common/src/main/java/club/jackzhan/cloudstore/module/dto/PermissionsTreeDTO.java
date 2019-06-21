package club.jackzhan.cloudstore.module.dto;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-06-21 11:23
 *
 * @Author: JackZhan
 */
@Data
public class PermissionsTreeDTO {
    private Integer id;
    private String name;
    private List<PermissionsTreeDTO> children;
    private List<Integer> checkList;
}
