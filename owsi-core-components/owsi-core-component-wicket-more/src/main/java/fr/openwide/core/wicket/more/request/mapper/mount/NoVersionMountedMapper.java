/*
 * Copyright 2010 by TalkingTrends (Amsterdam, The Netherlands)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://opensahara.com/licenses/apache-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package fr.openwide.core.wicket.more.request.mapper.mount;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.handler.ListenerInterfaceRequestHandler;
import org.apache.wicket.request.mapper.MountedMapper;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.parameter.PageParametersEncoder;

/**
 * Provides a mount strategy that drops the version number from stateful page
 * urls.
 */
public class NoVersionMountedMapper extends MountedMapper {
	public NoVersionMountedMapper(String path, Class<? extends IRequestablePage> pageClass) {
		super(path, pageClass, new PageParametersEncoder());
	}

	@Override
	protected void encodePageComponentInfo(Url url, PageComponentInfo info) {
		// do nothing so that component info does not get rendered in url
	}

	@Override
	public Url mapHandler(IRequestHandler requestHandler) {
		if (requestHandler instanceof ListenerInterfaceRequestHandler) {
			return null;
		} else {
			return super.mapHandler(requestHandler);
		}
	}
}