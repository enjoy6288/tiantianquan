<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>商品列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>


<script type="text/javascript">
	function productDetail(id) {
		var sendUrl = "${baseurl}product/productDetail.action?id=" + id;
		parent.opentabwindow(id + '商品详情', sendUrl);
	}

	function updateProduct(id) {
		var sendUrl = "${baseurl}product/updateProductView.action?id=" + id;
		parent.opentabwindow(id + '商品修改', sendUrl);
	}

	function changeProductStatus(id, status) {
		_confirm('您确认此操作吗？', topicProduct_callback, function() {
			var sendUrl = "${baseurl}product/updateProduct.action";
			var data = {
				"id" : id,
				"status" : status
			};
			jQuery.post(sendUrl, data, topicProduct_callback, "json");
		});
	}

	//主题商品操作之后的回调
	function topicProduct_callback(data) {
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			initGrid();
		}
	}

	//工具栏
	var toolbar = [];

	var frozenColumns;

	var columns = [ [

			{
				field : 'taobaoId',
				title : '淘宝ID',
				width : 100,
				align : 'center'
			},
			{
				field : 'img',
				title : '商品图片',
				width : 80,
				formatter : function(value, row, index) {
					return '<img style="width:100px;height:100px;" src="'+row.img+'" />';
				}
			},
			{
				field : 'title',
				title : '商品标题',
				width : 150,
				align : 'center'

			},
			{
				field : 'shelvesTime',
				title : '上架时间',
				width : 150,
				align : 'center',
				formatter : function(value, row, index) {
					if (value) {
						try {
							var date = new Date(value);
							var y = date.getFullYear();
							var m = date.getMonth() + 1;
							var d = date.getDate();
							var h = date.getHours();
							return y + "-" + m + "-" + d + " " + h + ":00:00";
						} catch (e) {
							alert(e);
						}
					}

				}
			},
			{
				field : 'sortValue',
				title : '排序值',
				width : 80,
				align : 'center'
			},
			{
				field : 'categoryName',
				title : '分类',
				width : 80,
				align : 'center'
			},
			{
				field : '8888',
				title : 'PV',
				width : 80,
				align : 'center'
			},
			{
				field : '8888',
				title : 'UV',
				width : 80,
				align : 'center'
			},
			{
				field : '8888',
				title : 'CRT',
				width : 80,
				align : 'center'
			},
			{
				field : 'opt2',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a href=javascript:productDetail("' + row.id
							+ '")>商品详情</a>';
				}
			},
			{
				field : 'opt3',
				title : '修改',
				width : 60,
				formatter : function(value, row, index) {
					return '<a href=javascript:updateProduct("' + row.id
							+ '")>修改</a>';
				}
			},
			{
				field : 'opt4',
				title : '暂停',
				width : 60,
				formatter : function(value, row, index) {
					if (row.status == 2) {
						return '<a id="change" href=javascript:changeProductStatus("'
								+ row.id + '","1")>恢复</a>';
					}
					if (row.status == 1) {
						return '<a id="change" href=javascript:changeProductStatus("'
								+ row.id + '","2")>暂停</a>';
					}
				}
			},
			{
				field : 'opt5',
				title : '删除',
				width : 60,
				formatter : function(value, row, index) {
					return '<a id="change" href=javascript:changeProductStatus("'
							+ row.id + '","3")>删除</a>';
				}
			}

	] ];

	function initGrid() {
		$('#topicProduct').datagrid({
			title : '商品列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/topic/topicProduct.action',
			queryParams : {//查询参数，只在加载时使用，点击查询使用load重新加载不使用此参数
				topicId : '${topicId}',
				type : '1'
			},
			//sortName : 'code',
			//sortOrder : 'desc',
			//remoteSort : false, 
			idField : 'id',//查询结果集主键采购单id
			//frozenColumns : frozenColumns,
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			autoRowHeight : false,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#topicProduct').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
	});
</script>
</HEAD>
<BODY>
	<form>

		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>

				<tr>
					<td>
						<table id="topicProduct"></table>
					</td>
				</tr>

			</TBODY>
		</TABLE>
	</form>


</BODY>
</HTML>

