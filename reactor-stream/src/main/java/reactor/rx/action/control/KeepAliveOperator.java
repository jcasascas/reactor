/*
 * Copyright (c) 2011-2016 Pivotal Software Inc, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package reactor.rx.action.control;

import org.reactivestreams.Subscriber;
import reactor.Publishers;
import reactor.core.subscriber.SubscriberBarrier;

/**
 * @author Stephane Maldini
 * @since 2.0, 2.1
 */
public final class KeepAliveOperator<O> implements Publishers.Operator<O, O> {

	public final static KeepAliveOperator INSTANCE = new KeepAliveOperator();

	@Override
	public Subscriber<? super O> apply(Subscriber<? super O> subscriber) {
		return new KeepAliveAction<>(subscriber);
	}

	final static class KeepAliveAction<O> extends SubscriberBarrier<O, O> {

		public KeepAliveAction(Subscriber<? super O> actual) {
			super(actual);
		}

		@Override
		protected void doCancel() {
			//IGNORE
		}
	}
}
