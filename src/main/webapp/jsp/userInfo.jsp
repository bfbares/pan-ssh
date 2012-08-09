<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="span4 offset4 well userinfo">
		<h1><s:property value="user.login"/></h1>
		<s:if test="%{(#session['user'].level.toString() == \"ADMIN\") || 
	              (#session['user'].level.toString() == \"GOD\")}">
			<h3><s:property value="user.level"/></h3>
		</s:if>
		<s:if test="!user.level.toString().equals(\"DISABLED\") &&
					!user.level.toString().equals(\"AUTODISABLED\")">
			<p><strong><s:text name="user.karma" />:</strong> <s:property value="user.karma"/></p>
			<s:if test="user.getUsername() != null && !user.getUsername().isEmpty()">
				<p><strong><s:text name="user.username" />:</strong> <s:property value="user.username"/></p>
			</s:if>
			<s:if test="%{(#session['user'].level.toString() == \"ADMIN\") || 
		              (#session['user'].level.toString() == \"GOD\")}">
				<p><strong><s:text name="user.email" />:</strong> <a href="<s:url value="mailto:%{user.email}" />" target="_blank"><s:property value="user.email"/></a></p>
			</s:if>
			<s:if test="user.getUrl() != null && !user.getUrl().isEmpty()">
				<p><strong><s:text name="user.url" />:</strong> <a href="<s:url value="%{user.url}" />" target="_blank"><s:property value="user.url"/></a></p>
			</s:if>
			<p><strong><s:text name="user.created" />:</strong> <s:date name="user.created"/></p>
			<p><strong><s:text name="user.lastlogin" />:</strong> <s:date name="user.lastlogin"/></p>
			<s:if test="%{(#session['user'].level.toString() == \"ADMIN\") || 
		              (#session['user'].level.toString() == \"GOD\")}">
		    	<p><strong><s:text name="user.ip" />:</strong> <s:property value="user.ip"/></p>
	        </s:if>
        </s:if>
        <div class="span2 offset2 levelch">
        	<s:if test="user.level.toString()!=\"GOD\"">
		        <s:if test="%{(#session['user'].level.toString() == \"ADMIN\") || 
			              (#session['user'].level.toString() == \"GOD\")}">
			    	<s:url action="user_disable" id="url">
		                <s:param name="id" value="user.userId" />
		            </s:url>
		            <p><s:a href="%{url}"><s:text name="user.level.change.disable"/></s:a></p>
			    </s:if>
			    <s:if test="%{#session['user'].level.toString() == \"GOD\"}">
			    	<s:url action="user_normal" id="url">
		                <s:param name="id" value="user.userId" />
		            </s:url>
		            <p><s:a href="%{url}"><s:text name="user.level.change.normal"/></s:a></p>
		            <s:url action="user_admin" id="url">
		                <s:param name="id" value="user.userId" />
		            </s:url>
		            <p><s:a href="%{url}"><s:text name="user.level.change.admin"/></s:a></p>
			    </s:if>	
		    </s:if>
	    </div>
	</div>
</div>