<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4 offset4 well">
		<h1><s:property value="user.login" /></h1>
		<p></p>
		<s:actionerror theme="bootstrap"/>
		<s:form theme="bootstrap" action="user_edit_save">
		    <s:textfield cssClass="input-xlarge" name="user.username" placeholder="getText('user.username')" tabIndex="1"/>
		    <s:textfield cssClass="input-xlarge" name="url" placeholder="getText('user.url')" tabIndex="2"/>
		    <s:password cssClass="input-xlarge" name="pass" placeholder="getText('user.pass')" tabIndex="3"/>
		    <p class="alert"><button class="close" data-dismiss="alert">×</button>
		    <strong><s:text name="warning.warning"/></strong>: <s:text name="warning.modifyPass"/></p>
		    <s:password cssClass="input-xlarge" name="newPass" placeholder="getText('uu.newPass')" tabIndex="4"/>
		    <s:password cssClass="input-xlarge" name="newPassConf" placeholder="getText('uu.newPassConf')" tabIndex="5"/>
		    <s:submit cssClass="btn btn-primary pull-right" value="%{getText('form.send')}" tabIndex="6"/>
		</s:form>
		<s:url action="user_autodisable" id="url" />
        <p><s:a href="%{url}"><s:text name="user.level.change.disable"/></s:a></p>
	</div>
</div>