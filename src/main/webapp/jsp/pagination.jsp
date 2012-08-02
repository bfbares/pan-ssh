<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="pagination pagination-centered">
  	<ul>
  		<s:if test="page == 0">
  			<li class="disabled"><a>«</a></li>
  			<li class="active"><a>1</a></li>
  		</s:if>
  		<s:else>
  			<s:url id="url">
            	<s:param name="page" value="%{page-1}" />
	        </s:url>
	  		<li><s:a href="%{url}">«</s:a></li>
	  		<s:url id="url">
            	<s:param name="page" value="0" />
	        </s:url>
	  		<li><s:a href="%{url}">1</s:a></li>
  		</s:else>
  		<s:if test="objectBlock.pagination.beginEllipsis">
  			<li class="disabled"><a>...</a></li>
  		</s:if>
		<s:iterator value="objectBlock.pagination.pageList" var="pageIt" status="pageListStatus">
			<s:url id="url">
	            <s:param name="page" value="#pageIt-1" />
	        </s:url>
	        <li 
	        	<s:if test="page == #pageIt-1">class="active" </s:if>
	        ><s:a href="%{url}"><s:property /></s:a></li>
		</s:iterator>
		<s:if test="objectBlock.pagination.endEllipsis">
  			<li class="disabled"><a>...</a></li>
  		</s:if>
		<s:if test="page == #attr['objectBlock.pagination.pageNum']-1">
			<s:if test="page != 0">
				<li class="active"><a><s:property value="page+1" /></a></li>
			</s:if>
  			<li class="disabled"><a>»</a></li>
  		</s:if>
  		<s:else>
  			<s:url id="url">
            	<s:param name="page" value="%{objectBlock.pagination.pageNum-1}" />
	        </s:url>
	  		<li><s:a href="%{url}"><s:property value="%{objectBlock.pagination.pageNum}" /></s:a></li>
  			<s:url id="url">
            	<s:param name="page" value="%{page+1}" />
	        </s:url>
	  		<li><s:a href="%{url}">»</s:a></li>
  		</s:else>
	</ul>
</div>