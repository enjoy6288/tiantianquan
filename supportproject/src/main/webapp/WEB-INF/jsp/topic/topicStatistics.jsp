<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>


<script type="text/javascript">
	function topicDetail(id) {
		/* 查询专题的商品 */
		var sendUrl = "${baseurl}/topic/topicProductView.action?topicId=" + id;
		parent.opentabwindow(id + '专题详情', sendUrl);
	}
	function topicAddProduct(id) {
		var sendUrl = "${baseurl}product/addProductView.action?topicId=" + id;
		parent.opentabwindow(id + '主题添加商品', sendUrl);
	}
	//工具栏
	var toolbar = [];

	var frozenColumns;

	var columns = [ [

			{
				field : 'id',
				title : '专题ID',
				width : 100,
				align : 'center'
			},
			{
				field : 'bannerOutterimg',
				title : 'banner',
				width : 80,
				formatter : function(value, row, index) {
					return '<img style="width:100px;height:100px;" src="'+row.bannerOutterimg+'" />';
				}
			},
			{
				field : 'topicName',
				title : '标题',
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
				field : 'pv',
				title : 'pv',
				width : 80,
				align : 'center'
			},
			{
				field : 'uv',
				title : 'uv',
				width : 80,
				align : 'center'
			},
			{
				field : 'ctr',
				title : 'ctr',
				width : 80,
				align : 'center'
			},
			{
				field : 'opt1',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					return '<a href=javascript:topicDetail("' + row.id
							+ '")>专题商品</a>';
				}
			}

	] ];

	var statisticsColumns = [ [

	{
		field : 'desc',
		title : '概览',
		width : 100,
		align : 'center'
	}, {
		field : 'pv',
		title : '专题总浏览量(PV)',
		width : 350,
		align : 'center'
	}, {
		field : 'uv',
		title : '专题独立访客(UV)',
		width : 350,
		align : 'center'
	}, {
		field : 'ctr',
		title : '专题商品总点击率(CTR)',
		width : 360,
		align : 'center'
	}, ] ];

	function initGrid() {
		$('#topicList').datagrid({
			title : '专题列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/topic/statistics.action',
			queryParams : {
				status : '${topic.status}'
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
				$('#topicList').datagrid('unselectRow', index);
			}
		});
		
		$('#statisticsList').datagrid({
			striped : true,
			url : '${baseurl}/product/statistics.action',
			queryParams : {
				type : '1'
			},
			columns : statisticsColumns,
			toolbar : toolbar,
			autoRowHeight : false,
			onClickRow : function(index, field, value) {
				$('#statisticsList').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
	});

	function queryTopic() {
		var formdata = $("#queryTopicForm").serializeJson();//将form中的input数据取出来
		$('#topicList').datagrid('load', formdata);
	}
	function topic60DaysView() {
		var sendUrl = "${baseurl}/topic/topic60DaysView.action";
		parent.opentabwindow('主题60天统计详情', sendUrl);
	}
</script>
</HEAD>
<BODY>
	<form id="queryTopicForm" name="queryTopicForm" method="post">
		<input type="hidden" name="status" value="${topic.status}" />
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="statisticsList"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		
		<TABLE class="table_search">
			<TBODY>
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

					<td height=30 width="15%" style="text-align: center">专题分类：</td>
					<td class=category width="25%" style="text-align: center"><select
						id="categoryId" name="categoryId" style="width: 150px">
							<option value="">全部</option>
							<c:forEach items="${categorys}" var="value">
								<option value="${value.id}">${value.categoryName}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr class=category>
					<td colspan=2 height=30 style="text-align: center"><a id="btn"
						href="#" onclick="queryProduct()" class="easyui-linkbutton"
						iconCls='icon-search'>查询</a></td>

					<td height=50 colspan=2 style="text-align: right"><a id="btn"
						href="#" onclick="topic60DaysView()" class="easyui-linkbutton"
						iconCls='icon-search'>查询60天统计</a></td>
				</tr>


			</TBODY>
		</TABLE>

		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="topicList"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>


</BODY>
</HTML>

