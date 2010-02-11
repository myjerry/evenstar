/**
 * myJerry | Evenstar
 * Copyright (C) 2010 myJerry Development Team
 * http://www.myjerry.org
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.myjerry.evenstar.constants;

import java.util.ArrayList;
import java.util.List;

public class DateHeaderConstants {
	
	public static final List<String> dateFormats = new ArrayList<String>();
	
	static {
		dateFormats.add("MMM d, yyyy");
		dateFormats.add("MMMM d, yyyy");
		dateFormats.add("EEEE, MMMM d, yyyy");
		dateFormats.add("MM/dd/yy");
		dateFormats.add("MM/dd/yyyy");
		dateFormats.add("MM.dd.yyyy");
		dateFormats.add("yyyyMMdd");
		dateFormats.add("yyyy/MM/dd");
		dateFormats.add("yyyy-MM-dd");
		dateFormats.add("dd.MM.yy");
		dateFormats.add("dd.MM.yyyy");
		dateFormats.add("dd/MM/yyyy");
		dateFormats.add("EEEE, MMMM dd");
		dateFormats.add("dd MMMM yyyy");
		dateFormats.add("dd MMMM, yyyy");
	}

}
