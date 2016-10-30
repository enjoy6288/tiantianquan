<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>修改专题</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<script type="text/javascript">
	function updateTopic() {
		var formObj = jQuery("#topicForm");
		var options = {
			dataType : "json",
			success : function(data) {
				updateTopic_callback(data);
			}
		};
		formObj.ajaxSubmit(options);
	}
	//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
	function updateTopic_callback(data) {
		//结果提示信息
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			window.location = '${baseurl}topic/queryTopicView.action?status=1';
		}
	}

	$(function() {
		//修改之前的值
		var goingTo = '${topic.goingTo}';
		$('#goingTo option').each(function() {
			if ($(this).text() == goingTo) {
				$(this).attr("selected", true);
			}
		});
		var value = $('#goingTo option:selected').html();
		if (value == '网页'||value == '主题内页') {
			$('#trLinkUrl').show();
		} else {
			$('#trLinkUrl').hide();
		}

		//修改之前的值
		var categoryId = '${topic.categoryId}';
		$('#categoryId option').each(function() {
			if ($(this).val() == categoryId) {
				$(this).attr("selected", true);
			}
		});

		//修改之前的值
		var dispalyPosition = '${topic.dispalyPosition}';
		$('#dispalyPosition option').each(function() {
			if ($(this).val() == dispalyPosition) {
				$(this).attr("selected", true);
			}
		});
	});
	
	function linkDisplay() {
		var value = $('#goingTo option:selected').html()
		if (value == '网页'||value == '主题内页') {
			$('#trLinkUrl').show();
		} else {
			$('#trLinkUrl').hide();
		}
	}
	
	function querySortValue(){
		$.post("${baseurl}topic/querySortValue.action",
		{
			"shelvesTime" : $('#shelvesTime').val()
		},
		function(data){
			document.getElementById("spanSortValue").innerText=data.sortValue;
		});
	}
	
	function sortValueExist(){
		$.post("${baseurl}topic/sortValueExist.action",
		{
			"shelvesTime" : $('#shelvesTime').val(),
			"sortValue" : $('#sortValue').val()
		},
		function(data){
			if(data.sortValueExist>0){
				var oldValue=${topic.sortValue};
				var newValue=$('#sortValue').val();
				if(oldValue!=newValue){
					alert("该排序值已经存在,请重新填写");
					$('#sortValue').val("");
				}
			}
			
		});
	}
</script>
</head>
<body>


	<form id="topicForm" action="${baseurl}topic/updateTopic.action"
		method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="${topic.id}" /> <input
			type="hidden" name="oldOut" value="${topic.bannerOutterimg}" /> <input
			type="hidden" name="oldInner" value="${topic.bannerInnerimg}" />
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%"
			bgColor=#c4d8ed>

			<TBODY>
				<tr>
					<td>
						<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
							align=center>
							<TBODY>
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 跳转至：</td>
									<td class=category width="90%">
										<div>
											<select id="goingTo" name="goingTo" onchange="linkDisplay()" style="width: 300px;">
												<c:forEach items="${goingTos}" var="value">
													<option value="${value.goingTo}">${value.name}</option>
												</c:forEach>
											</select>
										</div>
									</td>
								</tr>
								<tr id="trLinkUrl" style="display: none">
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 链接：</td>
									<td class=category width="90%">
										<div>
											<input id="linkUrl" name="linkUrl" style="width: 800px;"
												type="text"s value="${topic.linkUrl}"/>
										</div>
									</td>
								</tr>
								
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 展示位置：</td>
									<td class=category width="90%">
										<div>
											<select id="dispalyPosition" name="dispalyPosition"
												style="width: 300px;">
												<option value="" selected="selected">请选择</option>
												<option value="轮播banner">轮播banner</option>
												<option value="推荐专题">推荐专题</option>
												<option value="专题列表">专题列表</option>
											</select>
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 专题名称：</td>
									<td class=category width="90%">
										<div>
											<input id="topicName" name="topicName" style="width: 800px;"
												type="text" value="${topic.topicName}" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 专题副标题：</td>
									<td class=category width="90%">
										<div>
											<input id="topicViceName" name="topicViceName"
												style="width: 800px;" type="text"
												value="${topic.topicViceName}" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>专题描述：</td>
									<td class=category width="90%">
										<div>
											<input id="topicDesc" name="topicDesc" style="width: 800px;"
												type="text" value="${topic.topicDesc}" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 外部banner：</td>
									<td class=category width="90%">
										<div>
											<input type="file" name="out" />
										</div>

									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right>内部banner：</td>
									<td class=category width="90%">
										<div>
											<input type="file" name="inner" />
										</div>

									</td>
								</tr>
								
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 上架时间：</td>
									<td class=category width="90%">
										<div>
											<input id="shelvesTime" name="shelvesTime"
												onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
												style="width: 180px" value="<fmt:formatDate value="${topic.shelvesTime}" type="both"
												pattern="yyyy-MM-dd" />" onchange="querySortValue()"/>
										</div>
										<div id="shelvesTimeTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 排序值：</td>
									<td class=category width="90%">
										<div>
											<input id="sortValue" name="sortValue"
												onblur="sortValueExist()"  value="${topic.sortValue}" style="width: 100px;" type="text" />
										</div>
										<div id="sortValueTip"></div> <span id="spanSortValue"></span>
									</td>
								</tr>
								
								<tr>
									<td height=30 width="10%" align=right>浏览人数：</td>
									<td class=category width="90%">
										<div>
											<input id="scanActually" name="scanManual"
												style="width: 800px;" type="text"
												value="${topic.scanActually}" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>收藏人数：</td>
									<td class=category width="90%">
										<div>
											<input id="collectManual" name="collectManual"
												style="width: 800px;" type="text"
												value="${topic.collectManual}" />
										</div>
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 专题类别：</td>
									<td class=category width="90%" align=left><select
										id="categoryId" name="categoryId" style="width: 150px">
											<option value="">全部</option>
											<c:forEach items="${categorys}" var="value">
												<option value="${value.id}">${value.categoryName}</option>
											</c:forEach>
									</select></td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>备注：</td>
									<td class=category width="90%">
										<div>
											<textarea id="remarks" name="remarks" rows="3" cols="20"
												style="width: 600px;" value="${topic.remarks}"></textarea>
										</div>
									</td>
								</tr>

								<tr>
									<td colspan=2 align=center class=category><a
										id="submitbtn" class="easyui-linkbutton" iconCls="icon-ok"
										href="#" onclick="updateTopic()">修改</a></td>
								</tr>

							</TBODY>
						</TABLE>
					</td>
				</tr>
			</TBODY>
		</TABLE>
	</form>
</body>
</html>