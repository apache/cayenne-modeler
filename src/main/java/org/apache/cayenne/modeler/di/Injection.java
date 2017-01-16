package org.apache.cayenne.modeler.di;

import org.apache.cayenne.di.Injector;

public class Injection
{
    private static Injector injector;

    public static Injector getInjector()
    {
        return injector;
    }

    public static void setInjector(final Injector injector)
    {
        Injection.injector = injector;
    }

}
