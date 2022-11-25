package org.jcodec.codecs.vp8;

import org.jcodec.Utils;
import org.jcodec.codecs.vpx.VP8Decoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.ColorSpace;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import java.lang.System;
import java.nio.ByteBuffer;

public class VP8DecoderTest {

	private ByteBuffer bb;
	private VP8Decoder dec;

	@Ignore
	@Test
	public void _testKF() throws Exception {
		Picture pic = Picture.create(640, 480, ColorSpace.YUV420);
		Picture decoded = dec.decodeFrame(bb.duplicate(), pic.getData());

		AWTUtil.writePNG(decoded, Utils.tildeExpand("~/decoded.png"));
	}

	@Ignore
	@Test
	public void _testKFToPicture() throws Exception {
		Picture pic = Picture.create(640, 480, ColorSpace.YUV420);
		Picture decoded = dec.decodeFrame(bb.duplicate(), pic.getData());
		AWTUtil.writePNG(decoded, Utils.tildeExpand("~/decoded.pic.png"));
	}

	public void pysch() throws Exception {
		int mbWidth = 4;
		int mbHeight = 2;
		int stride = mbWidth * 16;

		int size = mbWidth * mbHeight * 16 * 16;
		for (int mbRow = 0; mbRow < mbWidth; mbRow++)
			for (int mbCol = 0; mbCol < mbHeight; mbCol++)

				for (int lumaRow = 0; lumaRow < 4; lumaRow++)
					for (int lumaCol = 0; lumaCol < 4; lumaCol++)
						for (int lumaPRow = 0; lumaPRow < 4; lumaPRow++)
							for (int lumaPCol = 0; lumaPCol < 4; lumaPCol++) {
								int lIdx = 16 * stride * mbRow + 4 * stride * lumaRow + stride * lumaPRow + 16 * mbCol
										+ 4 * lumaCol + lumaPCol;
								System.out.println("mbRow: " + mbRow + " mbCol: " + mbCol + " lumaRow: " + lumaRow
										+ " lumaCol: " + lumaCol + " lumaPRow: " + lumaPRow + " lumaPCol: " + lumaPCol
										+ " = " + lIdx);
								Assert.assertTrue("Must me smaller then " + size + " but was " + lIdx, (lIdx < size));
							}
		System.out.println(size);
	}

	@Before
	public void setUp() throws IOException {
		String path = "src/test/resources/fr.vp8";
		bb = NIOUtils.fetchFromFile(new File(path));
		System.out.println("byte array length: " + bb.remaining());
		dec = new VP8Decoder();
	}

}
