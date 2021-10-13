StereoDecodersTest1 : UnitTest {
	test_check_classname {
		var result = StereoDecoders.new;
		this.assert(result.class == StereoDecoders);
	}
}


StereoDecodersTester {
	*new {
		^super.new.init();
	}

	init {
		StereoDecodersTest1.run;
	}
}
