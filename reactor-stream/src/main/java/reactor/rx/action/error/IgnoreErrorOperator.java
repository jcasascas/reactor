/*
 * Copyright (c) 2011-2016 Pivotal Software Inc., Inc. All Rights Reserved.
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
package reactor.rx.action.error;

import org.reactivestreams.Subscriber;
import reactor.Publishers;
import reactor.core.subscriber.SubscriberBarrier;
import reactor.fn.Predicate;

/**
 * @author Stephane Maldini
 * @since 2.0, 2.1
 */
final public class IgnoreErrorOperator<T> implements Publishers.Operator<T, T> {

	private final Predicate<? super Throwable>   ignorePredicate;

	public IgnoreErrorOperator(Predicate<? super Throwable> ignorePredicate) {
		this.ignorePredicate = ignorePredicate;
	}

	@Override
	public Subscriber<? super T> apply(Subscriber<? super T> subscriber) {
		return new IgnoreErrorAction<>(subscriber, ignorePredicate);
	}

	final static class IgnoreErrorAction<T> extends SubscriberBarrier<T, T> {

		private final Predicate<? super Throwable>   ignorePredicate;

		IgnoreErrorAction(Subscriber<? super T> subscriber, Predicate<? super Throwable> ignorePredicate) {
			super(subscriber);
			this.ignorePredicate = ignorePredicate;
		}

		@Override
		protected void doError(Throwable cause) {
			if (!ignorePredicate.test(cause)) {
				subscriber.onError(cause);
			}else{
				onComplete();
			}
		}

		@Override
		public String toString() {
			return super.toString()+"{" +
					"ignorePredicate=" + ignorePredicate+
					'}';
		}
	}
}
