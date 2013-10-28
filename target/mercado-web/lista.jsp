<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:view>
	<!DOCTYPE html>
	<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="css/bootstrap.css" rel="stylesheet" media="screen">
<link href="css/estrutura.css" rel="stylesheet">
<script src="js/bootstrap.js"></script>
</head>
<body>
	<div class="navbar navbar-inverse">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Mercado - Betha Sistemas</a>
				<h:form>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li><h:commandLink action="carrinho">
									<h:outputText value="Fazer Compras" />
								</h:commandLink></li>
							<li><h:commandLink action="item">
									<h:outputText value="Itens" />
								</h:commandLink></li>
							<li class="active"><a href="#">Lista</a></li>
						</ul>
					</div>
				</h:form>
			</div>
		</div>
	</div>
	<div class="jumbotron">
		<h2>Para o gerenciamento e controle de suas compras!</h2>
		<p class="lead">No menu Lista você gerencia sua(s) lista(s) de
			compra(s).</p>
	</div>
	<div class="row">
		<div class="span7" style="margin-left: 40px;">
			<div class="container">
				<h:form styleClass="form-signin">
					<h:outputLabel value="Nome:" style="font-weight:bold" />
					<h:inputText value="#{listaController.lista.nome}" id="campNome"
						styleClass="input-block-level" style="width:220px;" />
					<h:outputLabel value="Itens:" style="font-weight:bold" />

					<h:selectManyListbox id="campoItens"
						value="#{listaController.listaItensTodos}"
						requiredMessage="Seleção obrigatória">
						<f:selectItems value="#{listaController.itens}" />
					</h:selectManyListbox>

					<h:selectManyListbox value="#{listaController.listaItensSele}"
						id="campoItensSele">
						<f:selectItems value="#{listaController.lista.selecItens}" />
					</h:selectManyListbox>

					<h:commandButton value="+"
						action="#{listaController.addListaItensSele}"
						styleClass="btn btn-primary" />
					<h:commandButton value="-"
						action="#{listaController.delListaItensSele}"
						styleClass="btn btn-primary" />


					<h:commandButton value="salvar" action="#{listaController.savar}"
						styleClass="btn btn-primary" />
				</h:form>
			</div>

		</div>
		<div class="span4">
			<h:form>
				<h:dataTable value="#{listaController.listas}" var="l"
					rendered="#{not empty listaController.listas}" border="0"
					styleClass="table table-striped" style="width:600px">
					<h:column>
						<f:facet name="header">
							<h:outputText value="Nome" />
						</f:facet>
						<h:commandLink value="#{l.nome}"
							action="#{listaController.editar}">
							<f:param name="id" value="#{l.id}" />
						</h:commandLink>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Cadastro" />
						</f:facet>
						<h:outputText value="#{l.cadastro}">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm" />
						</h:outputText>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Excluir" />
						</f:facet>
						<h:commandLink value="excluir" action="#{listaController.deletar}"
							styleClass="btn-small btn-danger">
							<f:param name="id" value="#{l.id}" />
						</h:commandLink>
					</h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Itens" />
						</f:facet>
						<h:dataTable value="#{l.itens}" var="i"
							rendered="#{not empty l.itens}" border="0">
							<h:column>
								<f:param name="id" value="#{i.nome}" />
							</h:column>
						</h:dataTable>
					</h:column>
				</h:dataTable>
			</h:form>
		</div>

	</div>
	<hr>
	<div id="footer">
		<div class="container">
			<p class="muted credit">
				Developed by <a href="http://calvinberschscherer.blogspot.com/">Calvin
					B. Scherer</a> for <a href="http://www.betha.com.br/">Betha
					Sistemas</a>.
			</p>
		</div>
	</div>
</body>
	</html>
</f:view>