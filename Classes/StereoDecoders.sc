StereoDecoderUHJ : PersistentMainFX {
  classvar <order, <encoder, <decoder;

  *new {|hoaOrder=3, addAfter=1|
    order = hoaOrder;

    ^this.init(addAfter);
  }

  *prepareResources{
    encoder = FoaEncoderMatrix.newHoa1;
    decoder = FoaDecoderKernel.newUHJ;
  }

  *synthFunc{
    ^{|out=0, bypass=0|
      var stereo, hoa, foa, sig;

      var freq = 30.0;  // highpass freq

      var hoaIn = In.ar(out, order.asHoaOrder.size);
      hoa = hoaIn;

      // exchange (ordering, normalisation)
      foa = FoaEncode.ar(
        hoa.keep(AtkFoa.defaultOrder.asHoaOrder.size),  // truncate to HOA1
        encoder
      );

      // exchange (reference radius)
      foa = FoaProximity.ar(
        HPF.ar(  // pre-condition FOA
          foa,
          freq
        ),
        AtkHoa.refRadius
      );

      // decode to stereo
      stereo = FoaDecode.ar(
        foa,
        decoder
      );

      // Pad output with silence after the stereo channels
      stereo = stereo ++ Silent.ar().dup(numChans-2);

      sig = Select.ar(which: bypass,  array: [stereo, hoaIn]);

      ReplaceOut.ar(bus: out,  channelsArray: sig);
    }

  }
}

StereoDecoderVirtualMics : StereoDecoderUHJ{
  classvar <micAngle, <micPattern;

  *new {|hoaOrder=3, addAfter=1, angle=131, pattern=0.5|
    micAngle = (angle / 2.0).degrad;
    micPattern = pattern;
    order = hoaOrder;

    ^this.init(addAfter);
  }

  *prepareResources{
    encoder = FoaEncoderMatrix.newHoa1;
    decoder = FoaDecoderMatrix.newStereo(micAngle, micPattern);
  }

}
