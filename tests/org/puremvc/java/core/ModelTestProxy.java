//
//  PureMVC Java Standard
//
//  Copyright(c) 2019 Saad Shams <saad.shams@puremvc.org>
//  Your reuse is governed by the Creative Commons Attribution 3.0 License
//

package org.puremvc.java.core;

import org.puremvc.java.patterns.proxy.Proxy;

public class ModelTestProxy extends Proxy {

    public static final String NAME = "ModelTestProxy";
    public static final String ON_REGISTER_CALLED = "onRegister Called";
    public static final String ON_REMOVE_CALLED = "onRemove Called";

    public ModelTestProxy() {
        super(NAME, "");
    }

    public void onRegister() {
        setData(ON_REGISTER_CALLED);
    }

    public void onRemove() {
        setData(ON_REMOVE_CALLED);
    }
}
