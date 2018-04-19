

SET FOREIGN_KEY_CHECKS=0;


DROP TABLE IF EXISTS `t_itdap_menu`;
CREATE TABLE `t_itdap_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `pid` int(11) NOT NULL COMMENT '父菜单ID，root菜单-1',
  `url` varchar(500) DEFAULT NULL COMMENT '跳转地址',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标样式',
  `type` varchar(100) DEFAULT NULL COMMENT '图标样式',
  `sort` int(1) NOT NULL DEFAULT '1' COMMENT '排序',
  `show_flag` varchar(1) DEFAULT 'Y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_itdap_menu
-- ----------------------------
INSERT INTO `t_itdap_menu` VALUES ('2', '实时数据监控', '-1', '#', 'fa fa-medkit', 'MANY', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('3', '非实时数据统计', '-1', '#', 'fa fa-desktop', 'MANY', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('4', '用户决策分析', '-1', '#', 'fa fa-th-list', 'MANY', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('5', '用户画像', '-1', '#', 'fa fa-suitcase', 'MANY', '4', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('6', '设备实时监控', '-1', '#', 'fa fa-camera-retro', 'MANY', '5', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('7', '系统配置', '-1', '#', 'fa fa-bar-chart-o', 'MANY', '6', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('10', '直播实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('11', '点播实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('12', '回看实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('13', '时移实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '4', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('14', 'EPG实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '5', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('15', '用户实时', '2', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '6', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('16', '直播非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('17', '点播非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('18', '回看非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('19', '时移非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '4', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('20', 'EPG非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '5', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('21', '用户非实时', '3', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '6', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('22', '产品维度分析', '4', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('23', 'CP维度分析', '4', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('24', '用户维度分析', '4', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('25', '数据推荐', '5', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('26', '数据画像', '5', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('27', '终端监控', '6', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '1', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('28', 'APP监控', '6', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('29', 'ELK节点监控1', '6', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('30', '配置项2', '7', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '4', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('31', '日志管理', '7', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('32', '用户角色', '7', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('36', '开发助手', '-1', '#', 'fa fa-briefcase', 'MANY', '0', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('38', '基本元素', '36', 'page/devToolsPages/ui-elements.html', 'fa fa-fw fa-table ', 'SINGLE', '2', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('39', '图表汇总', '36', 'page/devToolsPages/kibanaModel.html', 'fa fa-fw fa-table ', 'SINGLE', '3', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('40', '面板&选项卡', '36', 'page/devToolsPages/tab-panel.html', 'fa fa-fw fa-table ', 'SINGLE', '4', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('41', '表格大全', '36', 'page/devToolsPages/tableModelList.html', 'fa fa-fw fa-table ', 'SINGLE', '5', 'Y');
INSERT INTO `t_itdap_menu` VALUES ('42', '表单元素', '36', 'page/devToolsPages/form.html', 'fa fa-fw fa-table ', 'SINGLE', '6', 'Y');
