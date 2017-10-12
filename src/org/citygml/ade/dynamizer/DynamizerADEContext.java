package org.citygml.ade.dynamizer;

import java.util.Arrays;
import java.util.List;

import org.citygml.ade.dynamizer.bind.DynamizerADEMarshaller;
import org.citygml.ade.dynamizer.bind.DynamizerADEUnmarshaller;
import org.citygml.ade.dynamizer.model.module.DynamizerADEModule;
import org.citygml.ade.dynamizer.walker.DynamizerADEFeatureFunctionWalker;
import org.citygml.ade.dynamizer.walker.DynamizerADEFeatureWalker;
import org.citygml.ade.dynamizer.walker.DynamizerADEGMLFunctionWalker;
import org.citygml.ade.dynamizer.walker.DynamizerADEGMLWalker;
import org.citygml4j.model.citygml.ade.binding.ADEContext;
import org.citygml4j.model.citygml.ade.binding.ADEMarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEUnmarshaller;
import org.citygml4j.model.citygml.ade.binding.ADEWalker;
import org.citygml4j.model.module.ade.ADEModule;
import org.citygml4j.util.walker.FeatureFunctionWalker;
import org.citygml4j.util.walker.FeatureWalker;
import org.citygml4j.util.walker.GMLFunctionWalker;
import org.citygml4j.util.walker.GMLWalker;

public class DynamizerADEContext implements ADEContext {
	private final DynamizerADEMarshaller marshaller = new DynamizerADEMarshaller();
	private final DynamizerADEUnmarshaller unmarshaller = new DynamizerADEUnmarshaller();
	
	@Override
	public List<ADEModule> getADEModules() {
		return Arrays.asList(new ADEModule[]{DynamizerADEModule.v1_0});
	}

	@Override
	public List<String> getModelPackageNames() {
		return Arrays.asList(new String[]{"org.citygml.ade.dynamizer.model"});
	}

	@Override
	public List<String> getJAXBPackageNames() {
		return Arrays.asList(new String[]{"org.citygml.ade.dynamizer._1"});
	}

	@Override
	public ADEMarshaller getADEMarshaller() {
		return marshaller;
	}

	@Override
	public ADEUnmarshaller getADEUnmarshaller() {
		return unmarshaller;
	}

	@Override
	public ADEWalker<FeatureWalker> createDefaultFeatureWalker() {
		return new DynamizerADEFeatureWalker();
	}

	@Override
	public ADEWalker<GMLWalker> createDefaultGMLWalker() {
		return new DynamizerADEGMLWalker();
	}

	@Override
	public <T> ADEWalker<FeatureFunctionWalker<T>> createDefaultFeatureFunctionWalker() {
		return new DynamizerADEFeatureFunctionWalker<>();
	}

	@Override
	public <T> ADEWalker<GMLFunctionWalker<T>> createDefaultGMLFunctionWalker() {
		return new DynamizerADEGMLFunctionWalker<>();
	}

}
