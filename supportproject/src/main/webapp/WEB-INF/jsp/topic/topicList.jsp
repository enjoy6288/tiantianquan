<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>主题列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>


<script type="text/javascript">
	function updateTopic(id) {
		var sendUrl = "${baseurl}topic/updateTopicView.action?id=" + id;
		parent.opentabwindow(id+'专题修改', sendUrl);
	}
	function productDetail(id) {
		var sendUrl = "${baseurl}/product/queryProductView.action?type=1&topicId="
				+ id;
		parent.opentabwindow(id + '商品清单', sendUrl);
	}

	function changeTopicStatus(id, status) {
		_confirm('您确认此操作吗？', topicDel_callback, function() {
			var sendUrl = "${baseurl}topic/updateTopic.action";
			var data = {
				"id" : id,
				"status" : status
			};
			jQuery.post(sendUrl, data, topicDel_callback, "json");
		});
	}

	//删除商品的回调
	function topicDel_callback(data) {
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			queryTopic();
		}
	}

	//工具栏

	var toolbar = [];

	var frozenColumns;

	var columns = [ [

			{
				field : 'id',
				title : '活动ID',
				width : 100
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
				title : '专题标题',
				width : 150
			},
			{
				field : 'dispalyPosition',
				title : '展示位置',
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
				field : '备注',
				title : 'remarks',
				width : 60
			},
			{
				field : 'opt2',
				title : '商品详情',
				width : 60,
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
					if (row.status != 3) {
						return '<a href=javascript:updateTopic("' + row.id
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
						return '<a id="change" href=javascript:changeTopicStatus("'
								+ row.id + '","1")>恢复</a>';
					}
					if (row.status == 1) {
						return '<a id="change" href=javascript:changeTopicStatus("'
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
						return '<a id="change" href=javascript:changeTopicStatus("'
								+ row.id + '","3")>删除</a>';
					}
				}
			}

	] ];

	function initGrid() {
		$('#topicList').datagrid({
			title : '专题列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/topic/queryTopics.action',
			queryParams : {//查询参数，只在加载时使用，点击查询使用load重新加载不使用此参数
				status : '${status}'
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

	}
	$(function() {
		initGrid();
	});

	function queryTopic() {
		var formdata = $("#queryTopicForm").serializeJson();//将form中的input数据取出来
		$('#topicList').datagrid('load', formdata);
	}
</script>
</HEAD>
<BODY>
	<form id="queryTopicForm" name="queryTopicForm" method="post">
		<TABLE class="table_search">
			<input type="hidden" name="status" value="${status}" />
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

					<td height=30 width="15%" align=center>活动ID：</td>
					<td class=category width="25%" align=left>
						<div>
							<input id="id" name="id" style="width: 100%;" type="text"
								value="" />
						</div>
					</td>
				</tr>

				<tr>
					<td height=30 width="15%" align=center>专题分类：</td>
					<td class=category width="25%" align=left><select
						id="categoryId" name="categoryId" style="width: 150px">
							<option value="">全部</option>
							<c:forEach items="${categorys}" var="value">
								<option value="${value.id}">${value.categoryName}</option>
							</c:forEach>
					</select></td>

					<td height=30 width="15%" align=center>专题关键字：</td>
					<td class=category width="25%" align=left>
						<div>
							<input id="topicName" name="topicName" style="width: 100%;"
								type="text" />
						</div>
					</td>
				</tr>

				<tr>
					<td height=30 width="15%" align=right>展示位置：</td>
					<td class=category width="25%">
						<div>
							<select id="dispalyPosition" name="dispalyPosition"
								style="width: 300px;">
								<option value="" selected="selected">全部</option>
								<option value="轮播banner">轮播banner</option>
								<option value="推荐专题">推荐专题</option>
								<option value="专题列表">专题列表</option>
							</select>
						</div>
					</td>

					<td colspan=2 align=center class=category><a id="btn" href="#"
						onclick="queryTopic()" class="easyui-linkbutton"
						iconCls='icon-search'>查询</a></td>
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

