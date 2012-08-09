<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4 offset4 well">
		<h1><s:text name="section.linkeditform"/></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="link_update">
			<s:hidden name="link.linkId"/>
		    <s:textfield cssClass="input-xlarge" name="link.url" placeholder="getText('link.url')"/>
		    <s:textfield cssClass="input-xlarge" name="link.title" placeholder="getText('link.title')"/>
		    <s:textarea cssClass="input-xlarge" name="link.description" placeholder="getText('link.description')" rows="4"/>
		    <s:radio label="%{getText('link.selectcategory')}" name="categoryId" list="categories" listKey="categoryId" listValue="name" value="link.categoryId.categoryId"/>
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}"/>
		</s:form>
	</div>
</div>