/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.messagingservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;


import java.lang.ref.WeakReference;
import java.util.Iterator;

public class MessagingService extends Service {
    public static final int MSG_SEND_NOTIFICATION = 1;

    private final Messenger mMessenger = new Messenger(new IncomingHandler(this));

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    void displayText(String text) {
        MessageLogger.newMessage(text);
    }

    /**
     * Handler for incoming messages from clients.
     */
    private static class IncomingHandler extends Handler {
        private final WeakReference<MessagingService> mReference;

        IncomingHandler(MessagingService service) {
            mReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            MessagingService service = mReference.get();
            Bundle data = msg.getData();
            if(data != null) {
                if(data.getString("message") != null) {
                    String text = data.getString("message");
                    if (service != null) {
                        service.displayText(text);
                    }
                }
            }
        }
    }
}
