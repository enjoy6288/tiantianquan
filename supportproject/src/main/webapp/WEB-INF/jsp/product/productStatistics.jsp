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
				field : 'opt1',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a href=javascript:productDetail("' + row.id
							+ '")>商品详情</a>';
				}
			},

	] ];

	function initGrid() {
		$('#productList').datagrid({
			title : '商品列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/product/statistics.action',
			queryParams : {//查询参数，只在加载时使用，点击查询使用load重新加载不使用此参数
				status : '${product.status}',
				type : '${product.type}'
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
	function product60DaysView() {
		var sendUrl = "${baseurl}/product/product60DaysView.action";
		parent.opentabwindow('60天统计详情', sendUrl);
	}
</script>
</HEAD>
<BODY>
	<form id="queryProductForm" name="queryProductForm" method="post">
		<input type="hidden" name="status" value="${product.status}" /> <input
			type="hidden" name="topicId" value="${product.topicId}" />
		<TABLE class="table_search">
			<TBODY>
				<tr class=category width="15%">
					<td height=30 style="text-align: center">概览</td>
					<td height=30 style="text-align: center">首页总浏览量(PV)</td>
					<td height=30 style="text-align: center">首页独立访客(UV)</td>
					<td height=30 style="text-align: center">首页商品总点击率(CTR)</td>
				</tr>

				<tr class=category width="15%">
					<td height=80 style="text-align: center"><span
						style="font-weight: bold; font-size: 14pt; color: #00BFFF; font-family: 宋体">今日概览:</span></td>
					<td height=80 style="text-align: center"><span
						style="font-weight: bold; font-size: 14pt; color: #00BFFF; font-family: 宋体">8888</span></td>
					<td height=80 style="text-align: center"><span
						style="font-weight: bold; font-size: 14pt; color: #00BFFF; font-family: 宋体">8888</span></td>
					<td height=80 style="text-align: center"><span
						style="font-weight: bold; font-size: 14pt; color: #00BFFF; font-family: 宋体">8888</span></td>
				</tr>

				<tr class=category width="15%">
					<td height=30 style="text-align: center">昨日概览:</td>
					<td height=30 style="text-align: center">8888</td>
					<td height=30 style="text-align: center">8888</td>
					<td height=30 style="text-align: center">8888</td>
				</tr>

				<tr class=category width="15%">
					<td height=30 style="text-align: center">周平均概览:</td>
					<td height=30 style="text-align: center">8888</td>
					<td height=30 style="text-align: center">8888</td>
					<td height=30 style="text-align: center">8888</td>
				</tr>

				<!-- 查询所需字段 -->
				<tr class=category width="25%">
					<td height=50 colspan=4 style="text-align: right"><a id="btn"
						href="#" onclick="product60DaysView()" class="easyui-linkbutton"
						iconCls='icon-search'>查询60天统计</a></td>
				</tr>

				<tr>
					<td height=30 width="15%" style="text-align: center">时间：</td>
					<td class=category width="25%" style="text-align: center">
						<div>
							<INPUT id="begin" name="begin"
								onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
								style="width: 90%;" />

						</div>
						<div>
							<INPUT id="end" name="end"
								onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
								style="width: 100%;" />
						</div>
					</td>

					<td height=30 width="15%" style="text-align: center">商品分类：</td>
					<td class=category width="25%" style="text-align: center"><select
						id="categoryId" name="categoryId" style="width: 150px">
							<option value="">全部</option>
							<c:forEach items="${categorys}" var="value">
								<option value="${value.id}">${value.categoryName}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr class=category>
					<td colspan=4 height=30 style="text-align: center"><a id="btn"
						href="#" onclick="queryProduct()" class="easyui-linkbutton"
						iconCls='icon-search'>查询</a></td>


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


</BODY>
</HTML>

