/* 
 * Copyright (c) 2002-2004 LWJGL Project
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are 
 * met:
 * 
 * * Redistributions of source code must retain the above copyright 
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of 
 *   its contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.lwjgl;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * $Id$
 * <p>
 * A Swing adapter for using Swing to take care of things on platforms where we
 * know Swing is present.
 * <p><em>Note</em> To compile LWJGL applications with Excelsior JET that use JetPerfect
 * and that have no dependencies on Swing, do not include this class in your
 * JET project.
 * 
 * @author cix_foo <cix_foo@users.sourceforge.net>
 * @version $Revision$
 */
final class SwingAdapter implements PlatformAdapter {
	/**
	 * Constructs a new SwingAdapter
	 */
	SwingAdapter() {
	}

	/**
	 * Spawn a "modal" dialog in the centre of the screen with a message in it
	 * and an OK button. This method blocks until the dialog is dismissed.
	 * @param title Title of alert
	 * @param message Message to show in alert
	 */
	public void alert(String title, String message) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
		}
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
  
	/**
	 * Get the contents of the system clipboard. The system might not have a clipboard
	 * (particularly if it doesn't even have a keyboard) in which case we return null.
	 * Otherwise we return a String, which may be the empty string "".
	 * @return a String, or null if there is no system clipboard.
	 */
	public String getClipboard() {
		try {
			java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
			java.awt.datatransfer.Transferable transferable = clipboard.getContents(null);
			if (transferable.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.stringFlavor)) {
				return (String)transferable.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
			}
		} catch (Exception e) {
			Sys.log("Exception while getting clipboard: " + e);
		}
		return null;
	}
}
