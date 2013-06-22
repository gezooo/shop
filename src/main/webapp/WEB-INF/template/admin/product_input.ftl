<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商品 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	// 查询商品属性
	$("#productTypeId").change( function() {
		$(".productAttributeContentTr").remove();
		var productTypeId = $("#productTypeId").val();
		$.ajax({
			url: "product_attribute!ajaxProductAttribute.action",
			dataType: "json",
			data:{productTypeId: productTypeId},
			async: false,
			success: function(json) {
				var productAttributeTrHtml = "";
				$.each(json, function(i) {
					if(json[i]["attributeType"] == "text") {
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true}"' : ' class="formText"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "number") {
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, number: true}"' : ' class="formText {number: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "alphaint"){
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, lettersonly: true}"' : ' class="formText {lettersonly: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "select") {
						var productAttributeOption = '<option value="">请选择...</option>';
						for(var key in json[i]["attributeOptionList"]) {
							productAttributeOption += ('<option value="' + json[i]["attributeOptionList"][key] + '">' + json[i]["attributeOptionList"][key] + '</option>');
						}
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td><select name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="{required: true}"' : '') + '>' + productAttributeOption + '</select>' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "checkbox") {
						var productAttributeOption = "";
						for(var key in json[i]["attributeOptionList"]) {
							productAttributeOption += ('<label><input type="checkbox" name="' + json[i].id + '" value="' + json[i]["attributeOptionList"][key] + '"' + ((json[i].isRequired == true) ? ' class="{required: true, messagePosition: \'#' + json[i].id + 'MessagePosition\'}"' : '') +' />' + json[i]["attributeOptionList"][key] + '</label>&nbsp;&nbsp;');
						}
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td>' + productAttributeOption + ((json[i].isRequired == true) ? '<span id="' + json[i].id + 'MessagePosition"></span><label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "date") {
						productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText datePicker {required: true, dateISO: true}"' : ' class="formText datePicker {dateISO: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					}
				})
				$("#productTypeTr").after(productAttributeTrHtml);
				$.bindDatePicker();
			}
		});
	});
	
	// 商品图片预览滚动栏
	$(".productImageArea .scrollable").scrollable({
		speed: 600
	});
	
	// 显示商品图片预览操作层
	$(".productImageArea li").livequery("mouseover", function() {
		$(this).find(".productImageOperate").show();
	});
	
	// 隐藏商品图片预览操作层
	$(".productImageArea li").livequery("mouseout", function() {
		$(this).find(".productImageOperate").hide();
	});
	
	// 商品图片左移
	$(".left").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImagePrevLi = $productImageLi.prev("li");
		if ($productImagePrevLi.length > 0) {
			$productImagePrevLi.insertAfter($productImageLi);
		}
	});
	
	// 商品图片右移
	$(".right").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImageNextLi = $productImageLi.next("li");
		if ($productImageNextLi.length > 0) {
			$productImageNextLi.insertBefore($productImageLi);
		}
	});
	
	// 商品图片删除
	$(".delete").livequery("click", function() {
		var $productImageLi = $(this).parent().parent().parent();
		var $productImagePreview = $productImageLi.find(".productImagePreview");
		var $productImageIds = $productImageLi.find("input[name='productImageIds']");
		var $productImageFiles = $productImageLi.find("input[name='productImages']");
		var $productImageParameterTypes = $productImageLi.find("input[name='productImageParameterTypes']");
		$productImageIds.remove();
		$productImageFiles.after('<input type="file" name="productImages" hidefocus="true" />');
		$productImageFiles.remove();
		$productImageParameterTypes.remove();
		
		$productImagePreview.html("暂无图片");
		$productImagePreview.removeAttr("title");
		if ($.browser.msie) {
			if(window.XMLHttpRequest) {
				$productImagePreview[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = 'scale', src='')";
			}
		}
	});
	
	// 商品图片选择预览
	var $productImageScrollable = $(".productImageArea .scrollable").scrollable();
	var productImageLiHtml = '<li><div class="productImageBox"><div class="productImagePreview">暂无图片</div><div class="productImageOperate"><a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a><a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a><a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a></div><a class="productImageUploadButton" href="javascript: void(0);"><input type="file" name="productImages" hidefocus="true" /><div>上传新图片</div></a></div></li>';
	$(".productImageUploadButton input").livequery("change", function() {
		var $this = $(this);
		var $productImageLi = $this.parent().parent().parent();
		var $productImagePreview = $productImageLi.find(".productImagePreview");
		var fileName = $this.val().substr($this.val().lastIndexOf("\\") + 1);
		if (/(<#list systemConfig.allowedUploadImageExtension?split(stack.findValue("@net.shopxx.bean.SystemConfig@EXTENSION_SEPARATOR")) as list><#if list_has_next>.${list}|<#else>.${list}</#if></#list>)$/i.test($this.val()) == false) {
			$.message("您选择的文件格式错误！");
			return false;
		}
		$productImagePreview.empty();
		$productImagePreview.attr("title", fileName);
		if ($.browser.msie) {
			if(!window.XMLHttpRequest) {
				$productImagePreview.html('<img src="' + $this.val() + '" />');
			} else {
				$this[0].select();
				var imgSrc = document.selection.createRange().text;
				$productImagePreview[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = 'scale', src='" + imgSrc + "')";
			}
		} else if ($.browser.mozilla) {
			$productImagePreview.html('<img src="' + $this[0].files[0].getAsDataURL() + '" />');
		} else {
			$productImagePreview.html(fileName);
		}
		if ($productImageLi.next().length == 0) {
			$productImageLi.after(productImageLiHtml);
			if ($productImageScrollable.getSize() > 5) {
				$productImageScrollable.next();
			}
		}
		var $productImageIds = $productImageLi.find("input[name='productImageIds']");
		var $productImageParameterTypes = $productImageLi.find("input[name='productImageParameterTypes']");
		var $productImageUploadButton = $productImageLi.find(".productImageUploadButton");
		$productImageIds.remove();
		if ($productImageParameterTypes.length > 0) {
			$productImageParameterTypes.val("productImageFile");
		} else {
			$productImageUploadButton.append('<input type="hidden" name="productImageParameterTypes" value="productImageFile" />');
		}
	});

})
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加商品<#else>编辑商品</#if></h1>
		</div>
		<div class="blank"></div>
		<form id="inputForm" class="validate" action="<#if isAdd??>product!save.action<#else>product!update.action</#if>" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="id" value="${id}" />
			<ul class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="商品描述" hidefocus="true" />
				</li>
				<li>
					<input type="button" value="商品属性" hidefocus="true" />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						商品名称:
					</th>
					<td>
						<input type="text" name="product.name" class="formText {required: true}" value="${(product.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						货号:
					</th>
					<td>
						<input type="text" class="formText" name="product.productSn" value="${(product.productSn)!}" title="若留空则由系统随机生成" />
					</td>
				</tr>
				<tr>
					<th>
						商品分类:
					</th>
					<td>
						<select name="product.productCategory.id" class="{required: true}">
							<option value="">请选择...</option>
							<#list productCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == product.productCategory.id)!> selected</#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>------</#list>
									</#if>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						商品品牌:
					</th>
					<td>
						<select name="product.brand.id">
							<option value="">请选择...</option>
							<#list allBrand as list>
								<option value="${list.id}"<#if (list.id == product.brand.id)!> selected </#if>>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						本店售价:
					</th>
					<td>
						<input type="text" name="product.price" class="formText {required: true, min: 0}" value="${(product.price)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						市场售价:
					</th>
					<td>
						<input type="text" name="product.marketPrice" class="formText {required: true, min: 0}" value="${(product.marketPrice)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if systemConfig.pointType == "productSet">
					<tr>
						<th>
							积分:
						</th>
						<td>
							<input type="text" name="product.point" class="formText {required: true, digits: true}" value="${(product.point)!"0"}" />
						</td>
					</tr>
				</#if>
				<tr>
					<th>
						商品重量:
					</th>
					<td>
						<input type="text" name="product.weight" class="formText {required: true, min: 0, messagePosition: '#weightMessagePosition'}" value="${(product.weight)!"0"}" title="0表示不计重量" />
						<select name="product.weightUnit">
							<#list allWeightUnit as list>
								<option value="${list}"<#if (list == product.weightUnit)!> selected </#if>>
									${action.getText("WeightUnit." + list)}
								</option>
							</#list>
						</select>
						<span id="weightMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						库存量:
					</th>
					<td>
						<input type="text" name="product.store" class="formText {digits: true}" value="${(product.store)!}" title="只允许输入零或正整数，为空表示不计库存" />				 						
					</td>
				</tr>
				<tr>
					<th>
						是否精品推荐:
					</th>
					<td>
						<label><input type="radio" name="product.isBest" value="true"<#if (product.isBest == true)!> checked</#if> />是</label>
						<label><input type="radio" name="product.isBest" value="false"<#if (isAdd || product.isBest == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否新品推荐:
					</th>
					<td>
						<label><input type="radio" name="product.isNew" value="true"<#if (product.isNew == true)!> checked</#if> />是</label>
						<label><input type="radio" name="product.isNew" value="false"<#if (isAdd || product.isNew == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否热销推荐:
					</th>
					<td>
						<label><input type="radio" name="product.isHot" value="true"<#if (product.isHot == true)!> checked</#if> />是</label>
						<label><input type="radio" name="product.isHot" value="false"<#if (isAdd || product.isHot == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否上架:
					</th>
					<td>
						<label><input type="radio" name="product.isMarketable" value="true"<#if (isAdd || product.isMarketable == true)!> checked</#if> />是</label>
						<label><input type="radio" name="product.isMarketable" value="false"<#if (product.isMarketable == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						上传商品图片
					</th>
					<td>
						<div class="productImageArea">
							<div class="example"></div>
							<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
							<div class="scrollable">
								<ul class="items">
									<#list (product.productImageList)! as list>
										<li>
											<div class="productImageBox">
												<div class="productImagePreview png">
													<img src="${base}${list.thumbnailProductImagePath}" >
												</div>
												<div class="productImageOperate">
													<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
													<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
													<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
												</div>
												<a class="productImageUploadButton" href="javascript: void(0);">
													<input type="hidden" name="productImageIds" value="${list.id}" />
													<input type="hidden" name="productImageParameterTypes" value="productImageId" />
													<#if systemConfig.allowedUploadImageExtension != "">
														<input type="file" name="productImages" hidefocus="true" />
														<div>上传新图片</div>
													<#else>
														<div>不允许上传</div>
													</#if>
												</a>
											</div>
										</li>
									</#list>
									<li>
										<div class="productImageBox">
											<div class="productImagePreview png">暂无图片</div>
											<div class="productImageOperate">
												<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
												<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
												<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
											</div>
											<a class="productImageUploadButton" href="javascript: void(0);">
												<#if systemConfig.allowedUploadImageExtension != "">
													<input type="file" name="productImages" hidefocus="true" />
													<div>上传新图片</div>
												<#else>
													<div>不允许上传</div>
												</#if>
											</a>
										</div>
									</li>
								</ul>
							</div>
							<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
							<div class="blank"></div>
							<#if systemConfig.allowedUploadImageExtension != "">
								<span class="warnInfo"><span class="icon">&nbsp;</span><#if (systemConfig.uploadLimit) != 0 && (systemConfig.uploadLimit < 1024)>小于${systemConfig.uploadLimit}KB<#elseif (systemConfig.uploadLimit >= 1024)>小于${systemConfig.uploadLimit / 1024}MB</#if> (<#list systemConfig.allowedUploadImageExtension?split(stack.findValue("@net.shopxx.bean.SystemConfig@EXTENSION_SEPARATOR")) as list><#if list_has_next>*.${list};<#else>*.${list}</#if></#list>)</span>
							<#else>
								<span class="warnInfo"><span class="icon">&nbsp;</span>系统设置不允许上传图片文件!</span>
							</#if>
						</div>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" name="product.metaKeywords" class="formText" value="${(product.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea name="product.metaDescription" class="formTextarea">${(product.metaDescription)!}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<td colspan="2">
						<textarea name="product.description" class="wysiwyg" style="width: 100%; height: 450px;">${(product.description)!}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr id="productTypeTr">
					<th>
						商品类型:
					</th>
					<td>
						<select name="product.productType.id" id="productTypeId">
							<option value="">请选择...</option>
							<#list allProductType as list>
								<option value="${list.id}"<#if (list.id == product.productType.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<#list (product.productType.enabledProductAttributeList)! as list>
					<tr class="productAttributeContentTr">
						<th>
							${list.name}:
						</th>
						<td>
							<#if list.attributeType == "text">
								<input type="text" name="${list.id}" class="formText<#if list.isRequired> {required: true}</#if>" value="${(product.productAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "number">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>number: true}" value="${(product.productAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "alphaint">
								<input type="text" name="${list.id}" class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}" value="${(product.productAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "select">
								<select name="${list.id}"<#if list.isRequired> class="{required: true}"</#if>>
									<option value="">请选择...</option>
									<#list list.attributeOptionList as attributeOptionList>
										<option value="${attributeOptionList}"<#if (product.productAttributeMap.get(list)[0] == attributeOptionList)!> selected</#if>>${attributeOptionList}</option>
									</#list>
								</select>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "checkbox">
								<#list list.attributeOptionList as attributeOptionList>
									<label><input type="checkbox" name="${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="${attributeOptionList}"<#if (product.productAttributeMap.get(list).contains(attributeOptionList))!> checked</#if> />${attributeOptionList}</label>
								</#list>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "date">
								<input type="text" name="${list.id}" class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}" value="${(product.productAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>