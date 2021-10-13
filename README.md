# Stereo decoders for high order ambisonics

This repository contains easy to use stereo decoders for high order ambisonics using [the ambisonic toolkit for SuperCollider](https://www.ambisonictoolkit.net/) that are automatically set up as persistent main effects on the main outputs of SuperCollider. They are respawned when the user hard stops the sound.

This quark depends on the ambisonic toolkit and requires a full installation of it. See [these instructions](https://github.com/ambisonictoolkit/atk-sc3#installing) for more information. 

It also depends on [persistentmainfx]("https://github.com/madskjeldgaard/persistentmainfx") which is installed automatically by the quark system.

## Installation

Open up SuperCollider and evaluate the following line of code:
`Quarks.install("https://github.com/madskjeldgaard/stereodecoders")`

## Example usage

```supercollider
// Setup decoder
(
~order = ~order ? 3;
StereoDecoderUHJ.new(hoaOrder: ~order);
)

// Inspect the node tree to see that it is in fact in action:
s.plotTree

// Play some high order ambisonics sound
(
// White Noise going round
Ndef(\hoa_testorientation, {|amp=0.125, freq=100, rotFreq=1, ele=0|
	// var sig = LFTri.ar(freq)*amp;
	var sig = WhiteNoise.ar(amp);
	var azi = LFSaw.kr(rotFreq, mul: pi);

	sig = HoaEncodeDirection.ar(
		sig,
		azi,
		ele,
		AtkHoa.refRadius,
		~order
	);

}).play
)

// Bypass the decoder to output raw ambisonics
StereoDecoderUHJ.set(\bypass, 1);
```
