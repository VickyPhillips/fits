/* 
 * 
 * This file is part of FITS (File Information Tool Set).
 * 
 * FITS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FITS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FITS.  If not, see <http://www.gnu.org/licenses/>.
 */

/* Droid 6.1 has no nicely packaged way to make simple queries. This
 * class attempts to fill that gap for FITS, in a way that will let it
 * be lifted for other uses and perhaps incorporated into Droid itself.
 */
package edu.harvard.hul.ois.fits.tools.droid;

import java.io.File;
import java.util.List;

import uk.gov.nationalarchives.droid.core.BinarySignatureIdentifier;
import uk.gov.nationalarchives.droid.core.SignatureParseException;
import uk.gov.nationalarchives.droid.core.interfaces.IdentificationRequest;
//import uk.gov.nationalarchives.droid.core.interfaces.IdentificationResult;
import uk.gov.nationalarchives.droid.core.interfaces.IdentificationResultCollection;
import uk.gov.nationalarchives.droid.core.interfaces.RequestIdentifier;
import uk.gov.nationalarchives.droid.core.interfaces.archive.IdentificationRequestFactory;
import uk.gov.nationalarchives.droid.core.interfaces.resource.FileSystemIdentificationRequest;
import uk.gov.nationalarchives.droid.core.interfaces.resource.RequestMetaData;

public class DroidQuery {

    private File tempDir;
    private BinarySignatureIdentifier sigIdentifier;
    
    /** Create a DroidQuery object. This can be retained for any number of
     *  different queries.
     *  
     *  @param sigFile   File object for a Droid signature file
     *  @param tempDir   A temporary directory object
     *  
     *   @throws SignatureParseException 
     */
    public DroidQuery (File sigFile, File tempDir) throws SignatureParseException {
        this.tempDir = tempDir;
        sigIdentifier = new BinarySignatureIdentifier();
        sigIdentifier.setSignatureFile (sigFile.getAbsolutePath());
        sigIdentifier.init ();
    }
    
    /** Query a file and get back an XML response. */
    public IdentificationResultCollection queryFile (File fil) {
        RequestMetaData metadata = 
                new RequestMetaData(fil.length(), 
                        fil.lastModified(), 
                        tempDir.getAbsolutePath());
        RequestIdentifier identifier = new RequestIdentifier (fil.toURI());
        FileSystemIdentificationRequest req = 
                new FileSystemIdentificationRequest(metadata, identifier);
        IdentificationResultCollection results = 
                sigIdentifier.matchBinarySignatures(req);
//        List<IdentificationResult> resultsList = results.getResults();
        // This gives us an unfiltered list of matching signatures
        return results;
    }
}
