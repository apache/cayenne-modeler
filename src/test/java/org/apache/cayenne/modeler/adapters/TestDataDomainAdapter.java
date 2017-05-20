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

        dataDomainAdapter = new DataDomainAdapter(CayenneProjectManager.projectForPath("src/test/resources/cayenne-analytic.xml"));
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

    @Test
    public void testValidatingObjects()
    {
        Assert.assertEquals(Boolean.FALSE, dataDomainAdapter.getValidatingObjects());
        Assert.assertEquals(false, dataDomainAdapter.getCayennePropject().isDataDomainValidatingObjects());
        dataDomainAdapter.setValidatingObjects(Boolean.TRUE);
        Assert.assertEquals(Boolean.TRUE, dataDomainAdapter.getValidatingObjects());
        Assert.assertEquals(true, dataDomainAdapter.getCayennePropject().isDataDomainValidatingObjects());
    }

    @Test
    public void testSizeOfObjectCache()
    {
        Assert.assertEquals(10000, dataDomainAdapter.getSizeOfObjectCache().intValue());
        Assert.assertEquals(10000, dataDomainAdapter.getCayennePropject().getSizeOfObjectCache().intValue());
        dataDomainAdapter.setSizeOfObjectCache(20000);
        Assert.assertEquals(20000, dataDomainAdapter.getSizeOfObjectCache().intValue());
        Assert.assertEquals(20000, dataDomainAdapter.getCayennePropject().getSizeOfObjectCache().intValue());
    }

    @Test
    public void testUseSharedObjectCache()
    {
        Assert.assertEquals(true, dataDomainAdapter.getUseSharedCache());
        Assert.assertEquals(true, dataDomainAdapter.getCayennePropject().isUsingSharedCache());
        dataDomainAdapter.setUseSharedCache(false);
        Assert.assertEquals(false, dataDomainAdapter.getUseSharedCache());
        Assert.assertEquals(false, dataDomainAdapter.getCayennePropject().isUsingSharedCache());
    }
}