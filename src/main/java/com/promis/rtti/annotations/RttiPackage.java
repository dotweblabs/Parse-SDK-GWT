/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is GWT RTTI library.
 *
 * The Initial Developer of the Original Code is
 * Jan "SHadoW" Rames <ramejan@gmail.com>.
 * Portions created by the Initial Developer are Copyright (C) 2011
 * the Initial Developer. All Rights Reserved.
 */
package com.promis.rtti.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.promis.rtti.client.RttiObject;

/**
 * Use to specify packages that hold {@link RttiObject}s
 * Create package-info.java for required package like this:<br>
 * <code>@RttiPackage<br>
 * package com.some.data.package;<br>
 * <br>
 * import com.promis.rtti.annotations.RttiPackage;<br>
 * </code>
 * Subsequently all classes of this package will be probed
 * and descendants of {@link RttiObject} or classes
 * identified by {@link GenerateRtti} will get RTTI.
 * @author SHadoW
 *
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.PACKAGE})  
public @interface RttiPackage
{

}
