define(function(require, exports, module) {
	var base = require('base');
	var apiData, hotrule, crowl;
	//公用接口--数据路径
	exports.data_path = function(data, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/moduleInputPath/info',
			data : data,//{"moduleStage":2,"moduleId":2},
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}	
		})	
	}
	//公用接口--数据路径对应的字段设置
	exports.data_path_fileds = function(id,callback){
		base.ajax({
			url : '/bmw/moduleFields/moduleFields/info',
			data : {"pathId":id},
			type: 'post',
			success : function(data){
				callback(data.results.items,data)
			}
		})
	}
	//公用接口--详情中根据路径id获取具体路径
	exports.data_path_url = function(id,callback){
		base.ajax({
			url : '/bmw/moduleInputPath/getModuleInputPath/'+id,
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//活跃度模型--任务管理
	exports.model_active_list = function(callback){
		base.ajax({
			url : '/bmw/consumer/listActiveModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//活跃度模型--预测数据路径
	exports.model_active_predict_data = function(data, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/moduleInputPath/info',
			//data:{"moduleStage":2,"moduleId":1},
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//活跃度模型--特征训练数据路径
	exports.model_active_test_data = function(data, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/moduleInputPath/info',
			//data:{"moduleStage":2,"moduleId":1},
			data : data,
			type: 'post',
			success : function(data){
				if(data.msg=='无记录'){
					callback({},data)	
				}else{
					callback(data.results, data);
				}
			}	
		});	
	}
	//活跃度模型--手动输入参数
	exports.model_active_params = function(data, callback){
		base.ajax({
			url: '/bmw/moduleFields/moduleFields/info',
			//data:{"pathId":1, "moduleId":1}
			data:data,
			type:'post',
			success : function(data){
				if(data.msg=='无记录'){
					callback({},data);
				}else{
					callback(data.results.items,data);	
				}
			}
		});
	}
	//活跃度模型--创建任务
	exports.model_active_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/consumer/buildActiveModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//活跃度模型--删除
	exports.model_active_task_del = function(id,callback){
		base.ajax({
			url : '/bmw/consumer/deleteActiveModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//活跃度模型--查看结果
	exports.model_active_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/consumer/getActiveModelTask',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//购物篮模型--任务管理
	exports.sales_basket_list = function(callback){
		base.ajax({
			url : '/bmw/shoppingBasket/listShoppingBasketModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//购物篮模型--详情
	exports.sales_basket_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/shoppingBasket/getShoppingBasketModelTask',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		});
	}
	//购物篮模型--删除
	exports.sales_basket_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/shoppingBasket/deleteShoppingBasketModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//购物篮模型--创建任务
	exports.sales_basket_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/shoppingBasket/buildShoppingBasketModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data);	
			}
		});
	}
	//购物篮模型--图表展示
	exports.sales_basket_task_result = function(id,callback){
		base.ajax({
			url : '/bmw/shoppingBasket/getShoppingBasketModelResult',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data,data);
			}
		});
	}
	//交叉销售模型--任务列表
	exports.sales_cross_list = function(callback){
		base.ajax({
			url : '/bmw/crossSellingModel/listCrossSellingModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//交叉销售模型--详情
	exports.sales_cross_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/crossSellingModel/getCrossSellingModelTask',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//交叉销售模型--查看结果
	exports.sales_cross_task_result = function(id,callback){
		base.ajax({
			url : '/bmw/crossSellingModel/getCrossSellingModelResult',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.params,data);
			}
		})
	}
	//交叉销售模型--删除
	exports.sales_cross_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/crossSellingModel/deleteCrossSellingModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//交叉销售模型--创建任务
	exports.sales_cross_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/crossSellingModel/buildCrossSellingModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//交叉销售模型--预测数据路径
	exports.sales_cross_predict_data = function(data, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/moduleInputPath/info',
			//data:{"moduleStage":2,"moduleId":2},
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}	
		})	
	}

	//客户价值度模型--任务列表
	exports.customer_value_list = function(callback){
		base.ajax({
			url : '/bmw/rfmModelController/listRFMModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户价值度模型--详情
	exports.customer_value_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/rfmModelController/listRFMModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
				id : id,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户价值度模型--删除
	exports.customer_value_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/rfmModelController/deleteRFMModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//客户价值度模型--创建任务
	exports.customer_value_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/rfmModelController/buildRFMModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//客户价值度模型--预测数据路径
	exports.customer_value_predict_data = function(data, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/moduleInputPath/info',
			data : data,
			//data　: {moduleId:4,moduleStage:1},
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}	
		})	
	}
	//客户价值度模型--获取预测数据路径
	exports.customer_value_get_predict_data = function(inputPathId, callback){
		base.ajax({
			url : '/bmw/moduleInputPath/getModuleInputPath/'+inputPathId,
			//data　: {inputPathId:inputPathId},
			type: 'get',
			success : function(data){
				callback(data.results,data)
			}	
		})	
	}
	//忠诚度模型--任务列表
	exports.customer_loyalty_list = function(callback){
		base.ajax({
			url : '/bmw/loyaltyModelController/listLoyaltyModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//忠诚度模型--详情
	exports.customer_loyalty_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/loyaltyModelController/getLoyaltyModelTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//忠诚度模型--删除
	exports.customer_loyalty_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/loyaltyModelController/deleteLoyaltyModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//忠诚度模型--创建任务
	exports.customer_loyalty_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/loyaltyModelController/buildLoyaltyModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//活跃度模型--任务列表
	exports.customer_activity_list = function(callback){
		base.ajax({
			url : '/bmw/activityModelController/listActivityModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//活跃度模型--详情
	exports.customer_activity_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/activityModelController/getActivityModelTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//活跃度模型--删除
	exports.customer_activity_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/activityModelController/deleteActivityModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//活跃度模型--创建任务
	exports.customer_activity_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/activityModelController/buildActivityModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}

	//流失预警模型--任务列表
	exports.customer_warning_list = function(callback){
		base.ajax({
			url : '/bmw/lossWarningModelController/listLossWarningModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//流失预警模型--详情
	exports.customer_warning_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/lossWarningModelController/getLossWarningModelTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//流失预警模型--删除
	exports.customer_warning_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/lossWarningModelController/deleteLossWarningModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//流失预警模型--创建任务
	exports.customer_warning_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/lossWarningModelController/buildLossWarningModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//扩散模型--任务列表
	exports.customer_lookalike_list = function(callback){
		base.ajax({
			url : '/bmw/lookAlikeModel/listLookAlikeModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//扩散模型--详情
	exports.customer_lookalike_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/lookAlikeModel/getLookAlikeModelTask',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//扩散模型--删除
	exports.customer_lookalike_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/lookAlikeModel/deleteLookAlikeModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//扩散模型--创建任务
	exports.customer_lookalike_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/lookAlikeModel/buildLookAlikeModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//扩散模型--创建任务--扩散用户数据
	exports.data_path_byid = function(data,callback){
		base.ajax({
			url : '/bmw/moduleInputPath/getModuleInputPathByPid',
			data:data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//品牌转换分析模型--任务列表
	exports.market_conversion_list = function(callback){
		base.ajax({
			url : '/bmw/brandSwitchModelController/listBrandSwitchModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//品牌转换分析模型--详情
	exports.market_conversion_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/brandSwitchModelController/getBrandSwitchModelTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//品牌转换分析模型--删除
	exports.market_conversion_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/brandSwitchModelController/deleteBrandSwitchModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//品牌转换分析模型--创建任务
	exports.market_conversion_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/brandSwitchModelController/buildBrandSwitchModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//新产品联合分析模型--任务列表
	exports.product_analysis_list = function(callback){
		base.ajax({
			url : '/bmw/newProductsJointAnalysisModel/listNewProductsJointAnalysisModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//新产品联合分析模型--详情
	exports.product_analysis_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/newProductsJointAnalysisModel/getNewProductsJointAnalysisModelTask',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//getNewProductsJointAnalysisModelResult 图表结果
	//新产品联合分析模型--删除
	exports.product_analysis_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/newProductsJointAnalysisModel/deleteNewProductsJointAnalysisModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//新产品联合分析模型--创建任务
	exports.product_analysis_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/newProductsJointAnalysisModel/buildNewProductsJointAnalysisModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//客户细分模型--任务列表
	exports.customer_subdivision_list = function(callback){
		base.ajax({
			url : '/bmw/customerSegmentationModel/listCustomerSegmentationModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户细分模型--详情
	exports.customer_subdivision_task_view = function(data,callback){
		base.ajax({
			url : '/bmw/customerSegmentationModel/getCustomerSegmentationModelResult',
			data : data,
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户细分模型--类别名称修改
	exports.customer_subdivision_update_category_name = function(data,callback){	
		base.ajax({
			url : '/bmw/customerSegmentationModel/updateCustomerSegmentationModelResult',
			data : data,
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}

	//客户细分模型--删除
	exports.customer_subdivision_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/customerSegmentationModel/deleteCustomerSegmentationModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//客户细分模型--创建任务
	exports.customer_subdivision_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/customerSegmentationModel/buildCustomerSegmentationModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//客户异常行为识别模型--任务列表
	exports.customer_badBehavior_list = function(callback){
		base.ajax({
			url : '/bmw/customerBehaviourModel/listCustomerBehaviourModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户异常行为识别模型--详情
	exports.customer_badBehavior_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/customerBehaviourModel/getCustomerBehaviourTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户异常行为识别模型--删除
	exports.customer_badBehavior_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/customerBehaviourModel/deleteCustomerBehaviourModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//客户异常行为识别模型--创建任务
	exports.customer_badBehavior_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/customerBehaviourModel/buildCustomerBehaviourModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//客户黏度模型--任务列表
	exports.customer_viscosity_list = function(callback){
		base.ajax({
			url : '/bmw/customerGlueynessController/listCustomerGlueynessTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户黏度模型--详情
	exports.customer_viscosity_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/customerGlueynessController/getCustomerGlueynessTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//客户黏度模型--删除
	exports.customer_viscosity_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/customerGlueynessController/deleteCustomerGlueynessTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//客户黏度模型--创建任务
	exports.customer_viscosity_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/customerGlueynessController/buildCustomerGlueynessTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//广告归因模型--任务列表
	exports.market_attribution_list = function(callback){
		base.ajax({
			url : '/bmw/imputationController/listImputationTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//广告归因模型--详情
	exports.market_attribution_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/imputationController/getImputationTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//广告归因模型--删除
	exports.market_attribution_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/imputationController/deleteImputationTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//广告归因模型--创建任务
	exports.market_attribution_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/imputationController/buildImputationTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//社交网络活跃度--任务列表
	exports.service_sna_list = function(callback){
		base.ajax({
			url : '/bmw/socialNetworkCredit/listsocialNetworkCreditModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//社交网络活跃度--详情
	exports.service_sna_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/socialNetworkCredit/getsocialNetworkCreditTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//社交网络活跃度--删除
	exports.service_sna_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/socialNetworkCredit/deletesocialNetworkCreditModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//社交网络活跃度--创建任务
	exports.service_sna_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/socialNetworkCredit/buildsocialNetworkCreditModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//生命周期模型--任务列表
	exports.customer_lifecycle_list = function(callback){
		base.ajax({
			url : '/bmw/lifeCycleModelController/listLifeCycleModelTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//生命周期模型--详情
	exports.customer_lifecycle_task_view = function(id,callback){
		base.ajax({
			url : '/bmw/lifeCycleModelController/getLifeCycleModelTaskInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//生命周期模型--删除
	exports.customer_lifecycle_task_del = function(id, callback){
		base.ajax({
			url :'/bmw/lifeCycleModelController/deleteLifeCycleModelTaskLogically',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}	
		});	
	}
	//生命周期模型--创建任务
	exports.customer_lifecycle_task_add = function(data,callback){
		base.ajax({
			url : '/bmw/lifeCycleModelController/buildLifeCycleModelTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//生命周期模型--创建任务--数据路径对应的字段设置
	exports.customer_lifecycle_fileds = function(id,callback){
		base.ajax({
			url : '/bmw/lifeCycleRuleController/filterLifeCycleRuleTask',
			data : {"id":id},
			type: 'post',
			success : function(data){
				callback(data.results.items,data)
			}
		})
	}
	//生命周期模型--规则管理
	exports.customer_lifecycle_rule_list = function(callback){
		base.ajax({
			url : '/bmw/lifeCycleRuleController/listLifeCycleRuleTask',
			data : {
				currentPage : 1,
				pageSize : 10000,
			},
			type: 'post',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//生命周期模型--创建规则
	exports.customer_lifecycle_rule_add = function(data,callback){
		base.ajax({
			url : '/bmw/lifeCycleRuleController/buildLifeCycleRuleTask',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
	//生命周期模型--规则详情
	exports.customer_lifecycle_rule_info = function(id,callback){
		base.ajax({
			url : '/bmw/lifeCycleRuleController/getlifeCycleRuleInfo',
			data : {id:id},
			type: 'get',
			success : function(data){
				callback(data.results,data);
			}
		})
	}
	//生命周期模型--修改规则
	exports.customer_lifecycle_rule_update = function(data,callback){
		base.ajax({
			url : '/bmw/lifeCycleRuleController/updatelifeCycleRuleInfo',
			data : data,
			type: 'post',
			success : function(data){
				callback(data.results,data)
			}
		})
	}
})