package com.hunt.controller;

import com.hunt.model.dto.PageInfo;
import com.hunt.model.entity.SysOrganization;
import com.hunt.service.SystemOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import system.ResponseCode;
import system.Result;

/**
 * @Author ouyangan
 * @Date 2016/10/10/8:46
 * @Description
 */
@Controller
@RequestMapping("organization")
public class OrganizationController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private SystemOrganizationService systemOrganizationService;

    @RequestMapping(value = "toOrganization", method = RequestMethod.GET)
    public String toOrganization() {

        return "system/organization";
    }

    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public Result insert(@RequestParam String name,
                         @RequestParam String description,
                         @RequestParam long parentId,
                         @RequestParam int isFinal) {
        SysOrganization organization = new SysOrganization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setParentId(parentId);
        organization.setIsFinal(isFinal);
        long i = systemOrganizationService.insertOrganization(organization);
        if (i > 0) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ResponseBody
    @RequestMapping(value = "delelte", method = RequestMethod.GET)
    public Result delete(@RequestParam long id) {
        int i = systemOrganizationService.deleteOrganization(id);
        if (i == 2) {
            return Result.error(ResponseCode.can_not_delete.getMsg());
        } else if (i == 1) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result update(@RequestParam long id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam long parentId) {
        SysOrganization organization = new SysOrganization();
        organization.setId(id);
        organization.setName(name);
        organization.setDescription(description);
        organization.setParentId(parentId);
        int i = systemOrganizationService.updateOrganization(organization);
        if (i == 1) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ResponseBody
    @RequestMapping(value = "select", method = RequestMethod.POST)
    public PageInfo select(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "row", defaultValue = "15") int row,
                           @RequestParam(value = "parentId", defaultValue = "3") long id) {
        log.debug("select");
        PageInfo pageInfo = systemOrganizationService.selectPage(page, row, id);
        return pageInfo;
    }

}
