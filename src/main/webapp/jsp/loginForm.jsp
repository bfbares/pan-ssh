<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4"></div>
	<div class="span4 well">
		<h1><s:text name="section.login"/></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="login">
		    <s:textfield cssClass="input-xlarge" name="login" placeholder="getText('user.login')"/>
		    <s:password cssClass="input-xlarge" name="password" placeholder="getText('user.pass')"/>
		    <s:checkbox name="remember" label="%{getText('form.remember')}" />
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}"/>
		</s:form>
	</div>
</div>