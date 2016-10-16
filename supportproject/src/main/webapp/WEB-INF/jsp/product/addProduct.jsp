<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK rel="stylesheet" type="text/css"
	href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<title>添加用户</title>

<script type="text/javascript">
	$(function() {
		$.post("${baseurl}setting/goingto.action",
		{},
		function(data){
			document.getElementById("spanSortValue").innerText=data.sortValue;
		});
	});
	
	function saveProduct() {
		var formObj = jQuery("#productForm");
		var options = {
			dataType : "json",
			success : function(data) {
				saveProduct_callback(data);
			}
		};
		formObj.ajaxSubmit(options);
	}
	
	function linkDisplay() {
		var value = $('#goingTo option:selected').html()
		if (value == '网页'||value == '主题内页') {
			$('#trLinkUrl').show();
		} else {
			$('#trLinkUrl').hide();
		}
	}
	
	function querySortValue(){
		$.post("${baseurl}product/querySortValue.action",
		{
			"shelvesTime" : $('#shelvesTime').val()
		},
		function(data){
			document.getElementById("spanSortValue").innerText=data.sortValue;
		});
	}
	
	function sortValueExist(){
		$.post("${baseurl}product/sortValueExist.action",
		{
			"shelvesTime" : $('#shelvesTime').val(),
			"sortValue" : $('#sortValue').val()
		},
		function(data){
			if(data.sortValueExist>0){
				alert("该排序值已经存在,请重新填写");
				$('#sortValue').val("");
			}
			
		});
	}

	//ajax调用的回调函数，ajax请求完成调用此函数，传入的参数是action返回的结果
	function saveProduct_callback(data) {
		//结果提示信息
		message_alert(data);
		var type = data.resultInfo.type;
		if (type == 1) {
			window.location = '${baseurl}product/queryProductView.action?status=1';
		}

	}
	
	$(function() {
		$.formValidator.initConfig({formID:"productForm",debug:false,submitOnce:true,
			onSuccess:function(msg,obj,errorlist){
			}
		});
		$("#taobaoId").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#title").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#productDesc").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#img").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#priceNow").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#collectManual").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#shelvesTime").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
		$("#sortValue").formValidator({onShow:"请填写",onCorrect:"正确  "}).inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空"});
	});
</script>
</head>
<body>


	<form id="productForm" action="${baseurl}product/saveProduct.action"
		method="post" enctype="multipart/form-data">
		<!-- 该字段用于专题添加商品接受专题ID参数 -->
		<input type="hidden" name="topicId" value="${topicId}" />
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
											<select id="goingTo" name="goingTo" onchange="linkDisplay()"
												style="width: 300px;">
												<option value="">全部</option>
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
												type="text" />
										</div>
										<div id="linkUrlTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>淘宝ID：</td>
									<td class=category width="90%">
										<div>
											<input id="taobaoId" name="taobaoId" style="width: 800px;"
												type="text" />
										</div>
										<div id="taobaoIdTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品标题：</td>
									<td class=category width="90%">
										<div>
											<input id="title" name="title" style="width: 800px;"
												type="text" />
										</div>
										<div id="titleTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品描述：</td>
									<td class=category width="90%">
										<div>
											<input id="productDesc" name="productDesc"
												style="width: 800px;" type="text" />
										</div>
										<div id="productDescTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品图片：</td>
									<td class=category width="90%">
										<div>
											<input id="img" type="file" name="img" />
										</div>
										<div id="imgTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品现价：</td>
									<td class=category width="90%">
										<div>
											<input id="priceNow" name="priceNow" style="width: 800px;"
												type="text" />
										</div>
										<div id="priceNowTip"></div>
									</td>
								</tr>
								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 商品原价：</td>
									<td class=category width="90%">
										<div>
											<input id="priceOld" name="priceOld" style="width: 800px;"
												type="text" />
										</div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>收藏人数：</td>
									<td class=category width="90%">
										<div>
											<input id="collectManual" name="collectManual"
												style="width: 800px;" type="text" />
										</div>
										<div id="collectManualTip"></div>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right><span
										style="color: red;">*</span> 上架时间：</td>
									<td class=category width="90%">
										<div>
											<INPUT type="text" id="shelvesTime" name="shelvesTime"
												onfocus="WdatePicker({isShowWeek:false,skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
												style="width: 180px" onchange="querySortValue()" />
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
												onblur="sortValueExist()" style="width: 100px;" type="text" />
										</div>
										<div id="sortValueTip"></div> <span id="spanSortValue"></span>
									</td>
								</tr>

								<tr>
									<td height=30 width="10%" align=right>商品类别：</td>
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
												style="width: 600px;"></textarea>
										</div>
									</td>
								</tr>

								<tr>
									<td colspan=2 align=center class=category><a
										id="submitbtn" class="easyui-linkbutton" iconCls="icon-ok"
										href="#" onclick="saveProduct()">提交</a></td>
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