<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="section.firstrun"/></h1>
<p class="lead"><s:text name="fr.text"/></p>
<p></p>
<div class="row">
	<div class="span4"></div>
	<div class="span4 well">
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="firstrun_save">
		    <s:textfield cssClass="input-xlarge" name="user.login" placeholder="getText('user.login')"/>
		    <s:textfield cssClass="input-xlarge" name="user.email" placeholder="getText('user.email')"/>
		    <s:password cssClass="input-xlarge" name="user.password" placeholder="getText('user.pass')"/>
		    <s:password cssClass="input-xlarge" name="confirmPassword" placeholder="getText('fr.confirmPassword')"/>
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}"/>
		</s:form>
	</div>
</div>