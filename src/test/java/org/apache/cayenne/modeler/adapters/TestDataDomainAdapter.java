package org.apache.cayenne.modeler.adapters;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.cayenne.configuration.server.ServerModule;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.modeler.CayenneProjectManager;
import org.apache.cayenne.modeler.di.Injection;
import org.apache.cayenne.project.CayenneProjectModule;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDataDomainAdapter
{
    private static DataDomainAdapter dataDomainAdapter;;

    protected static Collection<Module> appendModules(final Collection<Module> modules)
    {
        modules.add(new ServerModule());
        modules.add(new CayenneProjectModule());
//        modules.add(new CayenneModelerModule());

        return modules;
    }

    @BeforeClass
    public static void loadProject()
    {
        Injection.setInjector(DIBootstrap.createInjector(appendModules(new ArrayList<Module>())));

        dataDomainAdapter = new DataDomainAdapter(CayenneProjectManager.projectForPath("src/main/resources/cayenne-analytic.xml"));
    }

    @Test
    public void testName()
    {
        Assert.assertEquals("analytic", dataDomainAdapter.getName());
        Assert.assertEquals("analytic", dataDomainAdapter.getCayennePropject().getDataDomainName());
        dataDomainAdapter.setName("analytic2");
        Assert.assertEquals("analytic2", dataDomainAdapter.getName());
        Assert.assertEquals("analytic2", dataDomainAdapter.getCayennePropject().getDataDomainName());
    }
}