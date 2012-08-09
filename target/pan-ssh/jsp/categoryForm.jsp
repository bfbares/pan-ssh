<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4 offset4 well">
		<h1><s:text name="section.categoryAdd"/></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="category_save">
			<s:if test="#parameters.isEmpty == false">
		        <s:hidden name="category.categoryId"/>
		    </s:if>
		    <s:textfield cssClass="input-xlarge" name="category.name" placeholder="getText('category.name')"/>
		    <s:select label="%{getText('category.selectparent')}" headerKey="-1" emptyOption="true" list="categories" listKey="categoryId" listValue="name" name="category.parent" />
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}"/>
		</s:form>
	</div>
</div>