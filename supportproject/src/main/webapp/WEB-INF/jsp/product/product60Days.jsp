<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>商品60天统计</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">
	//工具栏

	var toolbar = [];

	var frozenColumns;

	var columns = [ [ {
		field : 'date',
		title : '日期',
		width : 250,
		align : 'center'
	}, {
		field : 'PV',
		title : '首页总浏览量',
		width : 250,
		align : 'center'
	}, {
		field : 'UV',
		title : '首页独立访客',
		width : 250,
		align : 'center'
	}, {
		field : 'CTR',
		title : '首页商品总点击率',
		width : 250,
		align : 'center'
	}, ] ];

	function initGrid() {
		$('#product60Days').datagrid({
			title : '60天统计列表',
			striped : true,
			url : '${baseurl}/product/product60Days.action',
			idField : 'id',//查询结果集主键采购单id
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			autoRowHeight : false,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#product60Days').datagrid('unselectRow', index);
			}
		});

	}
	$(function() {
		initGrid();
	});
</script>
</HEAD>
<BODY>
	<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" align=center>
		<TBODY>
			<TR>
				<TD>
					<table id="product60Days"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>


</BODY>
</HTML>

