package io.hyperfoil.tools.yaup.json.graaljs;

import io.hyperfoil.tools.yaup.json.Json;
import io.hyperfoil.tools.yaup.json.ValueConverter;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

public class JsonProxyObject implements ProxyObject {


   private Json json;
   public JsonProxyObject(Json json){
      this.json = json;
   }

   public Json getJson(){return json;}

   @Override
   public Object getMember(String key) {
      if (json.has(key) && json.get(key) instanceof Json){
         return new JsonProxyObject(json.getJson(key));
      }
      return json.get(key);
   }

   @Override
   public Object getMemberKeys() {
      return json.keys().toArray();
   }

   @Override
   public boolean hasMember(String key) {
      return json.has(key);
   }

   @Override
   public void putMember(String key, Value value) {
      json.set(key, ValueConverter.convert(value));
   }

   @Override
   public boolean removeMember(String key) {
      boolean rtrn = json.has(key);
      json.remove(key);
      return rtrn;
   }

   @Override
   public String toString(){return json.toString(0);}
}
