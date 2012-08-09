<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4 offset4 well">
		<h1><s:text name="section.report" /></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="report_save">
			<s:hidden name="id" value="%{#parameters.id}"/>
		    <s:textarea cssClass="input-xlarge" name="report.reason" placeholder="getText('report.reason')" rows="5"/>
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}" />
		</s:form>
	</div>
</div>