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
	function updateProduct(id) {
		var sendUrl = "${baseurl}product/updateProductView.action?id=" + id;
		parent.opentabwindow(id + '商品修改', sendUrl);
	}

	function changeProductStatus(id, status) {
		_confirm('您确认此操作吗？', productDel_callback, function() {
			var sendUrl = "${baseurl}product/updateProduct.action";
			var data = {
				"id" : id,
				"status" : status
			};
			jQuery.post(sendUrl, data, productDel_callback, "json");
		});
	}
	
	function topicAddProduct(id) {
		var sendUrl = "${baseurl}product/addProductView.action?topicId=" + id;
		parent.opentabwindow(id + '主题添加商品', sendUrl);
	}

	//删除商品的回调
	function productDel_callback(data) {
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			queryProduct();
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
				width : 150

			},
			{
				field : 'priceNow',
				title : '现价',
				width : 80

			},
			{
				field : 'shelvesTime',
				title : '上架时间',
				width : 150,
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
				width : 80
			},
			{
				field : 'categoryName',
				title : '分类',
				width : 80
			},
			{
				field : 'remarks',
				title : '备注',
				width : 160
			},
			{
				field : 'opt3',
				title : '修改',
				width : 60,
				formatter : function(value, row, index) {
					if (row.status != 3) {
						return '<a href=javascript:updateProduct("' + row.id
								+ '")>修改</a>';
					}
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
					if (row.status != 3) {
						return '<a id="change" href=javascript:changeProductStatus("'
								+ row.id + '","3")>删除</a>';
					}
				}
			}

	] ];

	function initGrid() {
		$('#productList').datagrid({
			title : '商品列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/product/queryProducts.action',
			queryParams : {
				status : '${product.status}',
				type : '${product.type}',//该参数在这里固定为查询首页商品
				topicId : '${product.topicId}'//该参数在这里固定为查询首页商品
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
				$('#productList').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
	});

	function queryProduct() {
		var formdata = $("#queryProductForm").serializeJson();//将form中的input数据取出来
		$('#productList').datagrid('load', formdata);
	}
</script>
</HEAD>
<BODY>


	<form id="queryProductForm" name="queryProductForm" method="post">
		<input type="hidden" name="status" value="${product.status}" /> <input
			type="hidden" name="type" value="${product.type}" />

		<TABLE class="table_search">
			<TBODY>
				<tr>
					<td height=30 width="15%" align=center>上架时间：</td>
					<td class=category width="25%" align=left>
						<div>
							<INPUT id="shelvesTime" name="shelvesTime"
								onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
								style="width: 100%;" />
						</div>
					</td>

					<td height=30 width="15%" align=center>淘宝ID：</td>
					<td class=category width="25%" align=left>
						<div>
							<input id="taobaoId" name="taobaoId" style="width: 100%;"
								type="text" />
						</div>
					</td>
				</tr>

				<tr>
					<td height=30 width="15%" align=center>商品分类：</td>
					<td class=category width="25%" align=left><select
						id="categoryId" name="categoryId" style="width: 150px">
							<option value="">全部</option>
							<c:forEach items="${categorys}" var="value">
								<option value="${value.id}">${value.categoryName}</option>
							</c:forEach>
					</select></td>

					<td height=30 width="15%" align=center>标题关键字：</td>
					<td class=category width="25%" align=left>
						<div>
							<input id="title" name="title" style="width: 100%;" type="text" />
						</div>
					</td>
				</tr>
				<tr>
					<td colspan=4 style="text-align: center" class=category><a
						id="btn" href="#" onclick="queryProduct()"
						class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>

				</tr>

			</TBODY>
		</TABLE>

		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="productList"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>

	<c:if test="${not empty product.topicId}">
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>

				<tr>
					<td colspan=4 height=30 style="text-align: center"><a id="btn"
						href="javascript:topicAddProduct('${product.topicId}')"
						class="easyui-linkbutton" iconCls='icon-add'>该专题添加商品</a></td>
				</tr>
			</TBODY>
		</TABLE>
	</c:if>




</BODY>
</HTML>

