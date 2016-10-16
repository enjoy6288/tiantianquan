<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>主题60天统计</title>
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
		field : 'createTime',
		title : '日期',
		width : 250,
		align : 'center',
		formatter : function(value, row, index) {
				if (value) {
					try {
						var date = new Date(value);
						var y = date.getFullYear();
						var m = date.getMonth() + 1;
						var d = date.getDate();
						var h = date.getHours();
						return y + "-" + m + "-" + d;
					} catch (e) {
						alert(e);
					}
				}

			}
	}, {
		field : 'pv',
		title : '专题总浏览量',
		width : 250,
		align : 'center'
	}, {
		field : 'uv',
		title : '专题独立访客',
		width : 250,
		align : 'center'
	}, {
		field : 'ctr',
		title : '专题商品总点击率',
		width : 250,
		align : 'center'
	}, ] ];

	function initGrid() {
		$('#topic60Days').datagrid({
			title : '60天统计列表',
			striped : true,
			url : '${baseurl}/product/product60Days.action',
			queryParams : {
				type : '1'
			},
			idField : 'id',//查询结果集主键采购单id
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			autoRowHeight : false,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#topic60Days').datagrid('unselectRow', index);
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
					<table id="topic60Days"></table>
				</TD>
			</TR>
		</TBODY>
	</TABLE>


</BODY>
</HTML>

