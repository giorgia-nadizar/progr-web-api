(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4f25f185"],{"7db0":function(t,n,e){"use strict";var i=e("23e7"),r=e("b727").find,s=e("44d2"),a=e("ae40"),o="find",u=!0,l=a(o);o in[]&&Array(1)[o]((function(){u=!1})),i({target:"Array",proto:!0,forced:u||!l},{find:function(t){return r(this,t,arguments.length>1?arguments[1]:void 0)}}),s(o)},"8a79":function(t,n,e){"use strict";var i=e("23e7"),r=e("06cf").f,s=e("50c4"),a=e("5a34"),o=e("1d80"),u=e("ab13"),l=e("c430"),c="".endsWith,d=Math.min,f=u("endsWith"),m=!l&&!f&&!!function(){var t=r(String.prototype,"endsWith");return t&&!t.writable}();i({target:"String",proto:!0,forced:!m&&!f},{endsWith:function(t){var n=String(o(this));a(t);var e=arguments.length>1?arguments[1]:void 0,i=s(n.length),r=void 0===e?i:d(s(e),i),u=String(t);return c?c.call(n,u,r):n.slice(r-u.length,r)===u}})},"8e8d":function(t,n,e){"use strict";var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("b-overlay",{staticClass:"d-inline-block",attrs:{show:t.submitting,rounded:"",opacity:"0.6","spinner-small":"","spinner-variant":t.localVariant},on:{hidden:t.onHidden}},[e("b-button",{ref:"button",class:t.c,attrs:{size:t.localSize,disabled:t.disable,variant:t.localVariant},on:{click:function(n){return t.$emit("submit")}}},[t.icon?e("b-icon",{attrs:{icon:t.icon,"aria-hidden":"true"}}):t._e(),t._v(" "+t._s(t.text)+" ")],1)],1)},r=[],s={props:["submitting","icon","text","variant","size","disabled","c"],methods:{onHidden:function(){this.$refs.button.focus()}},computed:{localVariant:function(){return this.$props.variant?this.$props.variant:"primary"},localSize:function(){return this.$props.size?this.$props.size:null},disable:function(){return this.$props.submitting||this.$props.disabled}}},a=s,o=e("2877"),u=Object(o["a"])(a,i,r,!1,null,null,null);n["a"]=u.exports},9327:function(t,n,e){"use strict";e.r(n);var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("main",[e("h1",[t._v("Gestione "+t._s(t.title))]),e("b-button",{staticClass:"createNew",attrs:{variant:"outline-secondary",to:{name:"Crea "+t.ruolo}}},[e("b-icon",{attrs:{icon:"person-plus-fill","shift-h":"-4"}}),t._v("Crea un nuovo "+t._s(t.ruolo)+" ")],1),t.users.length?e("b-table",{attrs:{striped:"",outlined:"",stacked:"md",items:t.users,fields:t.fields,busy:t.loading,"head-variant":"dark"},scopedSlots:t._u([{key:"table-busy",fn:function(){return[e("div",{staticClass:"text-center my-2"},[e("b-spinner",{staticClass:"align-middle"}),e("strong",[t._v("Caricamento...")])],1)]},proxy:!0},{key:"cell(Azioni)",fn:function(n){return[e("b-button",{staticClass:"mr-2",attrs:{size:"sm",to:{name:"Modifica "+t.ruolo,params:{username:n.item.username}}}},[t._v("Modifica Dati")]),e("LoadingButton",{attrs:{size:"sm",variant:"secondary",submitting:n.item.deleting,text:"Elimina"},on:{submit:function(e){return t.askConfirm(n.item.username)}}})]}}],null,!1,647215361)}):t._e(),e("Confirm",{ref:"confirm",on:{confirm:t.deleteUser}})],1)},r=[],s=(e("4de4"),e("7db0"),e("4160"),e("8a79"),e("159b"),e("5530")),a=e("325d"),o=e("8e8d"),u=e("9420"),l=e("a18c"),c=e("2f62"),d={components:{Confirm:u["a"],LoadingButton:o["a"]},data:function(){return{users:[],fields:["username","nomeCognome","email","Azioni"],title:"",role:"",ruolo:"",loading:null}},created:function(){this.updatePage()},watch:{"$route.path":function(){this.updatePage()}},methods:Object(s["a"])(Object(s["a"])({},Object(c["b"])("alert",["success","error"])),{},{updatePage:function(){var t=this;this.loading=!0,this.$route.path.endsWith("listaUploaders")?(this.role="uploader",this.ruolo="Uploader",this.title="Uploaders"):this.$route.path.endsWith("listaAmministratori")?(this.role="administrator",this.ruolo="Amministratore",this.title="Amministratori"):l["a"].push("/".concat(a["a"].getLoggedUser().role,"/")),a["a"].getUsers(this.role).then((function(n){n.data.forEach((function(t){t.deleting=!1})),t.users=n.data,t.users=t.users.filter((function(t){return t.username!==a["a"].getLoggedUser().username})),t.loading=!1})).catch((function(){t.error("Impossibile proseguire sulla pagina richiesta"),l["a"].push("/".concat(a["a"].getLoggedUser().role,"/"))}))},askConfirm:function(t){this.$refs.confirm.askConfirm(t)},deleteUser:function(t){var n=this;this.users.find((function(n){return n.username===t})).deleting=!0,a["a"].deleteUser(t).then((function(){n.users=n.users.filter((function(n){return n.username!==t})),n.success("Eliminazione effettuata con successo")})).catch((function(){n.error("Eliminazione ".concat(n.ruolo.toLowerCase," fallita")),n.users.find((function(n){return n.username===t})).deleting=!1}))}})},f=d,m=e("2877"),h=Object(m["a"])(f,i,r,!1,null,null,null);n["default"]=h.exports},9420:function(t,n,e){"use strict";var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("b-overlay",{attrs:{show:t.busy,"no-wrap":""},on:{shown:t.onShown},scopedSlots:t._u([{key:"overlay",fn:function(){return[e("div",{ref:"dialog",staticClass:"text-center p-3",attrs:{tabindex:"-1",role:"dialog","aria-modal":"false","aria-labelledby":"form-confirm-label"}},[e("p",[e("strong",{attrs:{id:"form-confirm-label"}},[t._v("Confermare l'eliminazione?")])]),e("div",{staticClass:"d-flex justify-content-center"},[e("b-button",{staticClass:"mr-3",attrs:{variant:"outline-danger"},on:{click:t.onCancel}},[t._v(" Annulla ")]),e("b-button",{attrs:{variant:"outline-success"},on:{click:t.onOK}},[t._v("Conferma")])],1)])]},proxy:!0}])})},r=[],s={data:function(){return{busy:!1,username:null,id:null}},methods:{askConfirm:function(t,n){this.busy=!0,this.username=t,this.id=n},onShown:function(){this.$refs.dialog.focus()},onCancel:function(){this.busy=!1},onOK:function(){this.busy=!1,this.$emit("confirm",this.username,this.id)}}},a=s,o=e("2877"),u=Object(o["a"])(a,i,r,!1,null,null,null);n["a"]=u.exports}}]);
//# sourceMappingURL=chunk-4f25f185.36e21cae.js.map