define(function(require, exports, module) {
    var contentBoxLevel1 = '#container';
    var viewsPath = './views/';
    //消费者模型--活跃度模型
	exports['model_active'] = {
        container : contentBoxLevel1,
        url : viewsPath+'model/model_active.html'
    }
	//消费者模型--活跃度模型--添加任务
    exports['model_active_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'model/model_active_task_add.html',
		params : ['id']	
	}
	//消费者模型--活跃度模型--查看任务
	exports['model_active_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'model/model_active_task_view.html',
		params : ['id']	
	}
	//销售模型--购物篮分析模型
	exports['sales_basket'] = {
        container : contentBoxLevel1,
        url : viewsPath+'sales/sales_basket.html',
    }
	//销售模型--购物篮分析模型--添加任务
	exports['sales_basket_task_add'] = {
        container : contentBoxLevel1,
        url : viewsPath+'sales/sales_basket_task_add.html',
		params : ['id']
    }
	//销售模型--购物篮分析模型--查看任务
	exports['sales_basket_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'sales/sales_basket_task_view.html',
		params : ['id']	
	}
	//销售模型--交叉销售模型
	exports['sales_cross'] = {
        container : contentBoxLevel1,
        url : viewsPath+'sales/sales_cross.html',
    }
	//销售模型--交叉销售模型--添加任务
	exports['sales_cross_task_add'] = {
        container : contentBoxLevel1,
        url : viewsPath+'sales/sales_cross_task_add.html',
		params : ['id']
    }
	//销售模型--交叉销售模型--查看任务
	exports['sales_cross_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'sales/sales_cross_task_view.html',
		params : ['id']	
	}
	//客户分析模型--忠诚度-列表
	exports['customer_loyalty'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_loyalty.html',
	}
	//客户分析模型--忠诚度--创建任务
	exports['customer_loyalty_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_loyalty_task_add.html',
		params : ['id']	
	}
	//客户分析模型--忠诚度--查看结果
	exports['customer_loyalty_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_loyalty_task_view.html',
		params : ['id']	
	}
	//客户分析模型--客户价值度模型
	exports['customer_value'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_value.html'
    }
	//客户分析模型---客户价值度模型--添加任务
    exports['customer_value_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_value_task_add.html',
		params : ['id']	
	}
	//客户分析模型---客户价值度模型--查看任务
	exports['customer_value_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_value_task_view.html',
		params : ['id']	
	}
	//客户分析模型--客户流失预警模型
	exports['customer_warning'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_warning.html'
    }
	//客户分析模型---客户流失预警模型--添加任务
    exports['customer_warning_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_warning_task_add.html',
		params : ['id']	
	}
	//客户分析模型---客户流失预警模型--查看任务
	exports['customer_warning_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_warning_task_view.html',
		params : ['id']	
	}
	 //客户分析模型--活跃度模型
	exports['customer_activity'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_activity.html'
    }
	//客户分析模型--活跃度模型--添加任务
    exports['customer_activity_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_activity_task_add.html',
		params : ['id']	
	}
	//客户分析模型--活跃度模型--查看任务
	exports['customer_activity_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_activity_task_view.html',
		params : ['id']	
	}
	//客户分析模型--扩散模型
	exports['customer_lookalike'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lookalike.html'
    }
	//客户分析模型--扩散模型--添加任务
    exports['customer_lookalike_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lookalike_task_add.html',
		params : ['id']	
	}
	//客户分析模型--扩散模型--查看任务
	exports['customer_lookalike_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lookalike_task_view.html',
		params : ['id']	
	}
	//营销模型--品牌转换分析模型
	exports['market_conversion'] = {
        container : contentBoxLevel1,
        url : viewsPath+'market/market_conversion.html'
    }
	//营销模型--品牌转换分析模型
    exports['market_conversion_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'market/market_conversion_task_add.html',
		params : ['id']	
	}
	//营销模型--品牌转换分析模型
	exports['market_conversion_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'market/market_conversion_task_view.html',
		params : ['id']	
	}
	//产品模型--新产品联合分析模型
	exports['product_analysis'] = {
        container : contentBoxLevel1,
        url : viewsPath+'product/product_analysis.html'
    }
	//产品模型--新产品联合分析模型--添加任务
    exports['product_analysis_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'product/product_analysis_task_add.html',
		params : ['id']	
	}
	//产品模型--新产品联合分析模型--查看任务
	exports['product_analysis_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'product/product_analysis_task_view.html',
		params : ['id']	
	}
	//客户分析模型--客户细分模型
	exports['customer_subdivision'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_subdivision.html'
    }
	//客户分析模型--客户细分模型--添加任务
    exports['customer_subdivision_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_subdivision_task_add.html',
		params : ['id']	
	}
	//客户分析模型--客户细分模型--查看结果
	exports['customer_subdivision_list'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_subdivision_list.html',
		params : ['id']	
	}
	//客户分析模型--客户细分模型--查看任务
	exports['customer_subdivision_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_subdivision_task_view.html',
		params : ['clusterid', 'taskid']	
	}
	//客户分析模型--异常行为客户识别模型
	exports['customer_badBehavior'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_badBehavior.html'
    }
	//客户分析模型--异常行为客户识别模型--添加任务
    exports['customer_badBehavior_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_badBehavior_task_add.html',
		params : ['id']	
	}
	//客户分析模型--异常行为客户识别模型--查看任务
	exports['customer_badBehavior_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_badBehavior_task_view.html',
		params : ['id']	
	}

	//客户分析模型--客户黏度模型
	exports['customer_viscosity'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_viscosity.html'
    }
	//客户分析模型--客户黏度模型--添加任务
    exports['customer_viscosity_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_viscosity_task_add.html',
		params : ['id']	
	}
	//客户分析模型--客户黏度模型--查看任务
	exports['customer_viscosity_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_viscosity_task_view.html',
		params : ['id']	
	}
	//营销模型--广告归因模型
	exports['market_attribution'] = {
        container : contentBoxLevel1,
        url : viewsPath+'market/market_attribution.html'
    }
	//营销模型--广告归因模型
    exports['market_attribution_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'market/market_attribution_task_add.html',
		params : ['id']	
	}
	//营销模型--广告归因模型
	exports['market_attribution_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'market/market_attribution_task_view.html',
		params : ['id']	
	}
	//服务模型--社交网络活跃度分析模型
	exports['service_sna'] = {
        container : contentBoxLevel1,
        url : viewsPath+'service/service_sna.html'
    }
	//服务模型--社交网络活跃度分析模型
    exports['service_sna_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'service/service_sna_task_add.html',
		params : ['id']	
	}
	//服务模型--社交网络活跃度分析模型
	exports['service_sna_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'service/service_sna_task_view.html',
		params : ['id']	
	}
	//客户分析模型--生命周期模型
	exports['customer_lifecycle'] = {
        container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle.html'
    }
	//客户分析模型--生命周期模型--添加任务
    exports['customer_lifecycle_task_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle_task_add.html',
		params : ['id']	
	}
	//客户分析模型--生命周期模型--查看任务
	exports['customer_lifecycle_task_view'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle_task_view.html',
		params : ['id']	
	}
	//客户分析模型--生命周期模型--规则管理
	exports['customer_lifecycle_rule'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle_rule.html',
	}
	//客户分析模型--生命周期模型--规则管理--新建规则
	exports['customer_lifecycle_rule_add'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle_rule_add.html',
		params : ['id']	
	}
	//客户分析模型--生命周期模型--规则管理--配置预置规则
	exports['customer_lifecycle_rule_preset'] = {
		container : contentBoxLevel1,
        url : viewsPath+'customer/customer_lifecycle_rule_preset.html',
		params : ['id']	
	}
});
