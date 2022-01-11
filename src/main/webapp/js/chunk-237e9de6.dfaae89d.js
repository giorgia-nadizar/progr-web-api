(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-237e9de6"],{3016:function(e,t,r){"use strict";var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("b-form-group",{attrs:{label:"Password","label-align":"left","invalid-feedback":e.errorMsg,state:e.state}},[r("b-input-group",{scopedSlots:e._u([{key:"append",fn:function(){return[r("b-button",{attrs:{variant:"outline-secondary"},on:{click:e.togglePasswordVisibility}},[r("b-icon",{attrs:{icon:e.passwordIcon,"aria-hidden":"true"}})],1)]},proxy:!0}])},[r("b-form-input",{attrs:{type:e.passwordInputType,state:e.state},nativeOn:{change:function(t){return e.$emit("setPassword",e.password,e.$v.$invalid)}},model:{value:e.$v.password.$model,callback:function(t){e.$set(e.$v.password,"$model",t)},expression:"$v.password.$model"}})],1)],1)},s=[],a=r("b5ae"),i={data:function(){return{password:"",passwordInputType:"password",passwordIcon:"eye"}},validations:{password:{required:a["required"],minLength:Object(a["minLength"])(8)}},props:["touch"],methods:{togglePasswordVisibility:function(){"password"===this.passwordInputType?(this.passwordInputType="text",this.passwordIcon="eye-slash"):(this.passwordInputType="password",this.passwordIcon="eye")}},computed:{errorMsg:function(){return this.$v.password.$error&&!this.$v.password.required?"Campo obbligatorio":this.$v.password.$error&&!this.$v.password.minLength?"La password dev'essere lunga almeno 8 caratteri per motivi di sicurezza.":null},state:function(){return(!this.$v.password.$dirty||!this.$v.password.$invalid)&&null}},watch:{"$props.touch":function(){this.$v.password.$touch()}}},o=i,u=r("2877"),l=Object(u["a"])(o,n,s,!1,null,null,null);t["a"]=l.exports},"73cf":function(e,t,r){"use strict";r.r(t);var n=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("main",[r("h1",[e._v(e._s(e.title))]),r("b-form",{staticClass:"centered-form"},["consumer"===e.role?r("InputCodFiscaleConsumer",{attrs:{touch:e.touch},on:{setCF:e.setUsername}}):e._e(),"uploader"===e.role?r("InputUsernameUploader",{attrs:{touch:e.touch},on:{setUsername:e.setUsername}}):e._e(),"administrator"===e.role?r("InputEmailAmministratore",{attrs:{touch:e.touch},on:{setEmail:e.setEmailAdmin}}):e._e(),r("InputNomeCognome",{attrs:{touch:e.touch},on:{setName:e.setName}}),"administrator"!==e.role?r("InputEmail",{attrs:{touch:e.touch},on:{setEmail:e.setEmail}}):e._e(),r("InputPassword",{attrs:{touch:e.touch},on:{setPassword:e.setPassword}}),"uploader"===e.role?r("InputLogo",{attrs:{touch:e.touch},on:{setLogo:e.setLogo}}):e._e(),r("b-form-group",{staticClass:"form-buttons"},[r("LoadingButton",{attrs:{text:"Conferma registrazione",c:"spaced-buttons",submitting:e.submitting},on:{submit:e.handleSubmit}}),r("b-button",{attrs:{to:"/login",variant:"link"}},[e._v("Annulla")])],1)],1)],1)},s=[],a=(r("8a79"),r("5530")),i=r("2f62"),o=r("325d"),u=r("a18c"),l=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("b-form-group",{attrs:{label:"Codice Fiscale","label-align":"left","invalid-feedback":e.errorMsg,state:e.state}},[r("b-form-input",{staticClass:"upperCase",attrs:{state:e.state},nativeOn:{change:function(t){e.$emit("setCF",e.cf.toUpperCase(),e.$v.cf.$invalid)}},model:{value:e.$v.cf.$model,callback:function(t){e.$set(e.$v.cf,"$model",t)},expression:"$v.cf.$model"}})],1)},c=[],m=(r("96cf"),r("1da1")),d=r("b5ae"),h={data:function(){return{cf:""}},validations:{cf:{required:d["required"],isUnique:function(e){return Object(m["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,o["a"].isUnique(e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})))()},codFiscale:function(e){return/^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$/.test(e.toUpperCase())}}},props:["touch"],computed:{errorMsg:function(){return this.$v.cf.$error&&!this.$v.cf.required?"Campo obbligatorio":this.$v.cf.$error&&!this.$v.cf.codFiscale?"Inserire un Codice Fiscale valido":this.$v.cf.$error&&!this.$v.cf.unique?"Consumer già registrato":null},state:function(){return(!this.$v.cf.$dirty||!this.$v.cf.$invalid)&&null}},watch:{"$v.cf.$invalid":function(e){this.$emit("setCF",this.cf.toUpperCase(),e)},"$props.touch":function(){this.$v.cf.$touch()}}},p=h,v=r("2877"),$=Object(v["a"])(p,l,c,!1,null,null,null),f=$.exports,g=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("b-form-group",{attrs:{label:"Username","label-align":"left",description:"4 caratteri alfanumerici","invalid-feedback":e.errorMsg,state:e.state}},[r("b-form-input",{staticClass:"lowerCase",attrs:{state:e.state},nativeOn:{change:function(t){e.$emit("setUsername",e.username.toLowerCase(),e.$v.username.$invalid)}},model:{value:e.$v.username.$model,callback:function(t){e.$set(e.$v.username,"$model",t)},expression:"$v.username.$model"}})],1)},b=[],w={data:function(){return{username:""}},validations:{username:{required:d["required"],minLength:Object(d["minLength"])(4),maxLength:Object(d["maxLength"])(4),isUnique:function(e){return Object(m["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,o["a"].isUnique(e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})))()}}},props:["touch"],computed:{errorMsg:function(){return this.$v.username.$error&&!this.$v.username.required?"Campo obbligatorio":!this.$v.username.$error||this.$v.username.minLength&&this.$v.username.maxLength?this.$v.username.$error&&!this.$v.username.unique?"Username già utilizzato da un altro uploader":null:"Lo username dev'essere composto da esattamente 4 caratteri"},state:function(){return(!this.$v.username.$dirty||!this.$v.username.$invalid)&&null}},watch:{"$v.username.$invalid":function(e){this.$emit("setUsername",this.username.toLowerCase(),e)},"$props.touch":function(){this.$v.username.$touch()}}},C=w,U=Object(v["a"])(C,g,b,!1,null,null,null),I=U.exports,L=function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("b-form-group",{attrs:{label:"Indirizzo e-mail","label-align":"left","invalid-feedback":e.errorMsg,state:e.state}},[r("b-form-input",{staticClass:"lowerCase",attrs:{type:"email",state:e.state},nativeOn:{change:function(t){e.$emit("setEmail",e.email.toLowerCase(),e.$v.email.$invalid)}},model:{value:e.$v.email.$model,callback:function(t){e.$set(e.$v.email,"$model",t)},expression:"$v.email.$model"}})],1)},q=[],x={data:function(){return{email:""}},validations:{email:{required:d["required"],email:d["email"],isUnique:function(e){return Object(m["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,o["a"].isUnique(e);case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})))()}}},props:["touch"],computed:{errorMsg:function(){return this.$v.email.$error&&!this.$v.email.required?"Campo obbligatorio":this.$v.email.$error&&!this.$v.email.email?"Inserire un indirizzo e-mail valido":this.$v.email.$error&&!this.$v.email.unique?"Amministratore già registrato":null},state:function(){return(!this.$v.email.$dirty||!this.$v.email.$invalid)&&null}},watch:{"$v.email.$invalid":function(e){this.$emit("setEmail",this.email.toLowerCase(),e)},"$props.touch":function(){this.$v.email.$touch()}}},E=x,O=Object(v["a"])(E,L,q,!1,null,null,null),y=O.exports,_=r("0fb7"),k=r("65eb"),j=r("3016"),A=r("7a01"),P=r("8e8d"),z={components:{InputCodFiscaleConsumer:f,InputUsernameUploader:I,InputEmailAmministratore:y,InputNomeCognome:_["a"],InputEmail:k["a"],InputPassword:j["a"],InputLogo:A["a"],LoadingButton:P["a"]},data:function(){return{user:{username:"",nomeCognome:"",logo:"",email:"",password:""},invalidUsername:!0,invalidName:!0,invalidEmail:!0,invalidPassword:!0,submitting:!1,touch:!1}},computed:{role:function(){var e=this.$route.path;return e.endsWith("creaAmministratore")?"administrator":e.endsWith("creaUploader")?"uploader":"consumer"},title:function(){var e=this.$route.path;return e.endsWith("creaAmministratore")?"Crea Amministratore":e.endsWith("creaUploader")?"Crea Uploader":e.endsWith("creaConsumer")?"Crea Consumer":"Registrati"}},methods:Object(a["a"])(Object(a["a"])(Object(a["a"])({},Object(i["b"])("alert",["success","error"])),{},{setEmailAdmin:function(e){var t=!(arguments.length>1&&void 0!==arguments[1])||arguments[1];this.setEmail(e,t),this.setUsername(e,t)},setUsername:function(e){var t=!(arguments.length>1&&void 0!==arguments[1])||arguments[1];this.user.username=e,this.invalidUsername=t},setName:function(e,t){this.user.nomeCognome=e,this.invalidName=t},setEmail:function(e,t){this.user.email=e,this.invalidEmail=t},setPassword:function(e,t){this.user.password=e,this.invalidPassword=t},setLogo:function(e){this.user.logo=e}},Object(i["b"])("account",["register"])),{},{handleSubmit:function(){var e=this;this.touch=!0,this.invalidUsername||this.invalidName||this.invalidEmail||this.invalidPassword||(this.submitting=!0,this.user.role=this.role,o["a"].createUser(this.user).then((function(){"Registrati"===e.title?u["a"].push("/login"):u["a"].push("/".concat(o["a"].getLoggedUser().role,"/")),e.success("Creazione utente conclusa con successo."),e.submitting=!1})).catch((function(){e.error("Creazione utente fallita"),e.submitting=!1})))}})},F=z,M=Object(v["a"])(F,n,s,!1,null,null,null);t["default"]=M.exports}}]);
//# sourceMappingURL=chunk-237e9de6.dfaae89d.js.map