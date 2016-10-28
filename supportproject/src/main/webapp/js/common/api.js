define(function(require, exports, module) {
	var base = require('base');
	var apiData, hotrule, crowl;
	//首页
	exports.home_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/home/home.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//id类型统计
	exports.id_type_statistics = function(data,callback){
		base.ajax({
			url : '/anguanproject/home/idTypeStatistics.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//某种类型的id统计
	exports.single_id_type= function(data,callback){
		base.ajax({
			url : '/anguanproject/home/singleIdType.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//昨日新增虚拟人总数
	exports.total_count= function(data,callback){
		base.ajax({
			url : '/anguanproject/home/countTotalAndInc.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//近7天新增
	exports.supperid_count = function(data,callback){
		base.ajax({
			url : '/anguanproject/home/supperidCount.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//精确虚拟人检索
	exports.accurate_retrieval_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/accurateQuery.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	} ;
	//高级虚拟人检索条件
	exports.senior_retrieval_condition = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/queryXnrSearchType.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//模糊虚拟人检索
	exports.vague_retrieval_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/fuzzyQuery.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//根据GID获取网络行为刻画
	exports.supperId_detail = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/supperIdDetail.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	exports.permission_resource = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/getAllResource.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//添加权限
	exports.add_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/savePermission.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//更新权限
	exports.update_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/updatePermission.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改权限
	exports.modify_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/queryPermissionById.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//判断状态是否删除
	exports.judge_remove_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/delPermissionStatus.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//删除权限
	exports.remove_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/delPermission.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//查询权限获取列表
	exports.queryPermission_jurisdiction = function(data,callback){
		base.ajax({
			url : '/anguanproject/permission/queryPermission.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//添加角色
	exports.add_role = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/saveRole.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//获取所有权限
	exports.all_permission = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/getAllPermission.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//根据ID查询角色和权限名
	exports.query_rolename = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/queryRoleById.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改角色
	exports.modify_role = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/updateRole.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//判断是否删除角色
	exports.determine_remove_role = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/delRoleStatus.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//删除角色
	exports.remove_role = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/delRole.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//获取角色列表和查询
	exports.query_role_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/queryRole.action',
			data : data,
			type : 'POST',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//获取部门列表
	exports.query_department_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/queryDept.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//新建部门
	exports.add_department = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/saveDept.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//根据id获取部门信息
	exports.id_query_department = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/queryDeptById.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改部门
	exports.modify_department = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/updateDept.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//判断是否删除部门
	exports.determine_remove_department = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/delDeptStauts.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//删除部门管理
	exports.remove_department_manage = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/delDept.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//获取部门
	exports.gain_department = function(data,callback){
		base.ajax({
			url : '/anguanproject/dept/getDeptList.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//获取角色
	exports.gain_role = function(data,callback){
		base.ajax({
			url : '/anguanproject/role/getRoleList.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//保存用户
	exports.conserve_user = function(data,callback){
		base.ajax({
			url : '/anguanproject/user/saveUser.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//用户查询和获取用户列表
	exports.query_user_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/user/queryUser.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//导入ID类型
	exports.import_id = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/importData.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//根据id获取要编辑的用户信息
	exports.gain_user_message = function(data,callback){
		base.ajax({
			url : '/anguanproject/user/queryUserById.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改用户信息
	exports.modify_user = function(data,callback){
		base.ajax({
			url : '/anguanproject/user/updateUser.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//删除用户信息
	exports.remove_user = function(data,callback){
		base.ajax({
			url : '/anguanproject/user/delUser.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//获取虚拟人组列表和查询
	exports.query_monitor_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/queryMonitor.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//增加虚拟人组
	exports.add_monitor = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/saveMonitor.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改虚拟人组时获取当前虚拟人信息
	exports.obtain_monitor_message = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/queryMonitorById.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//修改虚拟人组
	exports.update_monitor = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/updateMonitor.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//判断是否删除虚拟人接口
	exports.judge_remove_monitor = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/delMonitorStatus.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//删除虚拟人接口
	exports.remove_monitor = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/delMonitor.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//上下线虚拟人组
	exports.up_down_monitor = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/onOrOffMonitor.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//增加虚拟人列表ID
	exports.add_monitor_id = function(data,callback){
		base.ajax({
			url : '/anguanproject/idType/saveIdType.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//虚拟人监测页面
	exports.monitor_idlog = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/queryMonitorIdLog.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};

	//查询id列表和获取列表
	exports.query_id_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/idType/queryIdType.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//判断是否删除id
	exports.status_delete_id = function(data,callback){
		base.ajax({
			url : '/anguanproject/idType/delIdTypeStatus.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	}
	//删除id
	exports.delete_id_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/idType/delIdType.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//ip属性库列表和查询
	exports.ip_attribute_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/ipProperty.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//ip属性库详情页
	exports.ip_detail = function(data,callback){
		base.ajax({
			url : '/anguanproject/xuni/ipDetail.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};


	//日志管理列表
	exports.journal_list = function(data,callback){
		base.ajax({
			url : '/anguanproject/log/queryLog.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//下载模板
	exports.downloadTemp = function(data,callback){
		base.ajax({
			url : '/anguanproject/monitor/downloadTemp.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	};
	//获取id类型
	exports.get_all_id = function(data,callback){
		base.ajax({
			url : '/anguanproject/idType/getAllIdTypes.action',
			data : data,
			type : 'post',
			contentType:"application/json",
			success : function(data){
				callback(data)
			}
		})
	}
}) 
